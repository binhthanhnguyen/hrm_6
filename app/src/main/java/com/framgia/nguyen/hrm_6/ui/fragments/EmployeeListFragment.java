package com.framgia.nguyen.hrm_6.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.daos.EmployeeDAO;
import com.framgia.nguyen.hrm_6.models.Employee;
import com.framgia.nguyen.hrm_6.ui.adapters.EmployeeListAdapter;
import com.framgia.nguyen.hrm_6.ui.widget.recyclerview.DividerItemDecoration;
import com.framgia.nguyen.hrm_6.ui.widget.recyclerview.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binh on 6/6/16.
 */
public class EmployeeListFragment extends Fragment {
    private static final String TAG = "EmployeeListFragment";
    public static final int PER_PAGE = 30;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private List<Employee> mEmployees = new ArrayList<>();
    private EmployeeListAdapter mEmployeeListAdapter;
    private EmployeeDAO mEmployeeDAO;
    private String mQuery;

    public static EmployeeListFragment newInstance() {
        EmployeeListFragment fragment = new EmployeeListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        mEmployeeDAO = EmployeeDAO.getInstance(getContext());
        mEmployees = mEmployeeDAO.getEmployees(0, PER_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_list, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if(mQuery == null) {
                    mEmployees.addAll(mEmployeeDAO.getEmployees(totalItemsCount, PER_PAGE));
                } else {
                    mEmployees.addAll(mEmployeeDAO.searchEmployees(mQuery, totalItemsCount, PER_PAGE));
                }
                mEmployeeListAdapter.notifyItemRangeChanged(totalItemsCount, mEmployees.size() - 1);
            }
        });
        setupAdapter();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.employee_fragment_menu, menu);
        final MenuItem searchItem = (MenuItem) menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query.trim();
                new SearchEmployeeTask(mQuery).execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery(mQuery, false);
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                mQuery = null;
                updateUi();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    private void setupAdapter() {
        mEmployeeListAdapter = new EmployeeListAdapter(mEmployees);
        if (isAdded()) mRecyclerView.setAdapter(mEmployeeListAdapter);
    }

    private void updateUi() {
        mEmployees.clear();
        if (mQuery == null) {
            mEmployees.addAll(EmployeeDAO.getInstance(getContext()).getEmployees(0, PER_PAGE));
        } else {
            mEmployees.addAll(EmployeeDAO.getInstance(getContext()).searchEmployees(mQuery, 0, PER_PAGE));
        }
        if(isAdded()) mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    private class SearchEmployeeTask extends AsyncTask<Void, Void, List<Employee>> {
        private String mQuery;

        SearchEmployeeTask(String query) {
            mQuery = query;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected List<Employee> doInBackground(Void... params) {
            return mEmployeeDAO.searchEmployees(mQuery, 0, PER_PAGE);
        }

        @Override
        protected void onPostExecute(List<Employee> employees) {
            mEmployees.clear();
            mEmployees.addAll(employees);
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}
