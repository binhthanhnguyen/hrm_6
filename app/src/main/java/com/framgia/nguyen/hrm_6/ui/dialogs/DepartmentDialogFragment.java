package com.framgia.nguyen.hrm_6.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.framgia.nguyen.hrm_6.R;

/**
 * Created by binh on 6/27/16.
 */
public class DepartmentDialogFragment extends DialogFragment {
    private EditText mEditName;
    private EditText mEditDescription;
    private OnNewDepartmentListener mListener;

    public interface OnNewDepartmentListener {
        public void onSave(String name, String description);
    }

    public void setOnNewDepartmentListener( OnNewDepartmentListener onNewDepartmentListener) {
        mListener = onNewDepartmentListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_department, null, false);
        mEditName = (EditText) view.findViewById(R.id.edit_name);
        mEditDescription = (EditText) view.findViewById(R.id.edit_description);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_new_department)
                .setView(view)
                .setPositiveButton(R.string.btn_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = mEditName.getText().toString();
                        String desc = mEditDescription.getText().toString();
                        if (!name.isEmpty()) {
                            mListener.onSave(name, desc);
                        }
                        dismiss();
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}
