package com.framgia.nguyen.hrm_6.ui.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.models.Department;
import com.framgia.nguyen.hrm_6.ui.activities.DepartmentDetailActivity;

import java.util.List;

/**
 * Created by binh on 6/6/16.
 */
public class DepartmentListAdapter extends RecyclerView.Adapter<DepartmentListAdapter.DepartmentHolder> {
    private List<Department> mDepartments;

    public DepartmentListAdapter(List<Department> departments) {
        mDepartments = departments;
    }

    @Override
    public DepartmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.department_list_item, parent, false);
        return new DepartmentHolder(view);
    }

    @Override
    public void onBindViewHolder(final DepartmentHolder holder, int position) {
        final Department department = mDepartments.get(position);
        holder.bindDepartmentItem(department);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DepartmentDetailActivity.newIntent(v.getContext(), department);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDepartments.size();
    }

    public class DepartmentHolder extends RecyclerView.ViewHolder{
        private View mView;
        private TextView mTextName;
        private TextView mTextDesc;

        public DepartmentHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mTextName = (TextView) itemView.findViewById(R.id.text_name);
            mTextDesc = (TextView) itemView.findViewById(R.id.text_desc);
        }

        public void bindDepartmentItem(Department department) {
            mTextName.setText(department.getName());
            mTextDesc.setText(department.getDesc());
        }
    }
}
