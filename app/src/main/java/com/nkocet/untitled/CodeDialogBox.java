package com.nkocet.untitled;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;

public class CodeDialogBox extends AppCompatDialogFragment {

    TextInputEditText codeTextView;
    DialogBoxListener listener;

    /* This part of code is responsible for showing a dialog box
     * that takes a 6 digit code as input */

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.code_dialog_box, null);
        codeTextView = view.findViewById(R.id.code);
        dialog.setView(view)
                .setTitle("Enter Code")
                .setPositiveButton("Proceed", (dialog1, which) -> {
                    if (TextUtils.isEmpty(codeTextView.getText()))
                        codeTextView.setError("Code is necessary!");
                    else {
                        if (codeTextView.getText().toString().length() != 6)
                            codeTextView.setError("Code must be exactly 6 digits!");
                        else listener.getCode(codeTextView.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", (dialog12, which) -> {
                });
        return dialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (DialogBoxListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must be implemented!");
        }
    }

    public interface DialogBoxListener {
        void getCode(String code);
    }
}
