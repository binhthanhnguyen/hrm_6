package com.framgia.nguyen.hrm_6.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.daos.EmployeeDAO;

/**
 * Created by binh on 6/27/16.
 */
public class DeleteEmployeeDialogFragment extends DialogFragment {
    private static final String ARGUMENT_EMPLOYEE_ID = "ARGUMENT_EMPLOYEE_ID";

    public static DeleteEmployeeDialogFragment newInstance(int employeeId) {
        DeleteEmployeeDialogFragment dialog = new DeleteEmployeeDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_EMPLOYEE_ID, employeeId);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_confirm_delete)
                .setMessage(R.string.message_confirm_delete)
                .setPositiveButton(R.string.btn_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int employeeId = getArguments().getInt(ARGUMENT_EMPLOYEE_ID);
                        if (EmployeeDAO.getInstance(getContext()).delete(employeeId)) {
                            Toast.makeText(getContext(), R.string.message_delete_success, Toast.LENGTH_LONG).show();
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), R.string.message_delete_fail, Toast.LENGTH_LONG).show();
                            dismiss();
                        }
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}
