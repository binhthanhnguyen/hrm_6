package com.framgia.nguyen.hrm_6.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.daos.EmployeeDAO;
import com.framgia.nguyen.hrm_6.models.Department;
import com.framgia.nguyen.hrm_6.models.Employee;
import com.framgia.nguyen.hrm_6.ui.adapters.EmployeeListAdapter;
import com.framgia.nguyen.hrm_6.ui.widget.recyclerview.DividerItemDecoration;
import com.framgia.nguyen.hrm_6.ui.widget.recyclerview.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binh on 6/7/16.
 */
public class DepartmentDetailActivity extends AppCompatActivity {
    private static final String TAG = "DepartmentDetailActivity";
    public static final int PER_PAGE = 30;

    private static final String EXTRA_DEPARTMENT = "EXTRA_DEPARTMENT";
    private List<Employee> mEmployees;
    private RecyclerView mRecyclerView;
    private Department mDepartment;
    private EmployeeDAO mEmployeeDAO;
    private EmployeeListAdapter mEmployeeListAdapter;
    private ImageButton mButtonAdd;
    private SearchView mSearchView;
    private ProgressBar mProgressBar;
    private String mQuery;

    public static Intent newIntent(Context context, Department department) {
        Intent intent = new Intent(context, DepartmentDetailActivity.class);
        intent.putExtra(EXTRA_DEPARTMENT, department);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mDepartment = (Department) intent.getSerializableExtra(EXTRA_DEPARTMENT);
        mEmployeeDAO = EmployeeDAO.getInstance(getApplicationContext());
        mEmployees = mEmployeeDAO.findEmployeesByDepartmentId(mDepartment.getId(), 0, PER_PAGE);
        setupView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EmployeeActivity.NEW_EMPLOYEE_REQUEST) {
            updateUi();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUi();
    }

    private void setupView() {
        TextView textName = (TextView) findViewById(R.id.text_name);
        textName.setText(mDepartment.getName());
        TextView textDesc = (TextView) findViewById(R.id.text_desc);
        textDesc.setText(mDepartment.getDesc());
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setupRecyclerView();

        mButtonAdd = (ImageButton) findViewById(R.id.button_add_employee);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployeeActivity.newIntent(v.getContext(), mDepartment.getId());
            }
        });

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                new SearchEmployeeTask(query).execute();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mQuery = null;
                updateUi();
                return true;
            }
        });
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mEmployeeListAdapter = new EmployeeListAdapter(mEmployees);
        mRecyclerView.setAdapter(mEmployeeListAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                if(mQuery == null) {
                    mEmployees.addAll(mEmployeeDAO.findEmployeesByDepartmentId(mDepartment.getId(), totalItemsCount, PER_PAGE));
                } else {
                    mEmployees.addAll(mEmployeeDAO.findEmployeesByName(mDepartment.getId(), mQuery, totalItemsCount, PER_PAGE));
                }
                mEmployeeListAdapter.notifyItemRangeChanged(totalItemsCount, mEmployees.size() -1);
            }
        });
    }

    private void updateUi() {
        mEmployees.clear();
        if (mQuery == null) {
            mEmployees.addAll(mEmployeeDAO.findEmployeesByDepartmentId(mDepartment.getId(), 0, PER_PAGE));
        } else {
            mEmployees.addAll(mEmployeeDAO.findEmployeesByName(mDepartment.getId(), mQuery, 0, PER_PAGE));
        }
        mRecyclerView.getAdapter().notifyDataSetChanged();
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
            return mEmployeeDAO.findEmployeesByName(mDepartment.getId(), mQuery, 0, PER_PAGE);
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
