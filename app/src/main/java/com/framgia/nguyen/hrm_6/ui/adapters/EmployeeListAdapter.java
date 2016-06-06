package com.framgia.nguyen.hrm_6.ui.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.models.Employee;
import com.framgia.nguyen.hrm_6.ui.activities.EmployeeDetailActivity;
import java.util.List;

/**
 * Created by binh on 6/7/16.
 */
public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeHolder> {
    private List<Employee> mEmployees;

    public EmployeeListAdapter(List<Employee> employees) {
        mEmployees = employees;
    }

    @Override
    public EmployeeListAdapter.EmployeeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.employee_list_item, parent, false);
        return new EmployeeHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeListAdapter.EmployeeHolder holder, int position) {
        final Employee employee = mEmployees.get(position);
        holder.bindEmployeeItem(employee);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmployeeDetailActivity.newIntent(v.getContext(), employee);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }

    public class EmployeeHolder extends RecyclerView.ViewHolder {
        private View mView;
        private TextView mTextId;
        private TextView mTextName;
        private TextView mTextPosition;

        public EmployeeHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTextId = (TextView) itemView.findViewById(R.id.text_id);
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextPosition = (TextView) itemView.findViewById(R.id.text_position);
        }

        public void bindEmployeeItem(Employee employee) {
            mTextId.setText(String.valueOf(employee.getId()));
            mTextName.setText(employee.getName());
            mTextPosition.setText(employee.getPosition().desc());
        }
    }
}
