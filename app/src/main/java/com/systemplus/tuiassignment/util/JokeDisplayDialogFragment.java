package com.systemplus.tuiassignment.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.systemplus.tuiassignment.R;

import static com.systemplus.tuiassignment.util.TUIConstants.MESSAGE_BODY;

public class JokeDisplayDialogFragment extends DialogFragment {


    public JokeDisplayDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static JokeDisplayDialogFragment newInstance(String message) {
        JokeDisplayDialogFragment frag = new JokeDisplayDialogFragment();
        Bundle args = new Bundle();
        args.putString(MESSAGE_BODY, message);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString(MESSAGE_BODY);
        if (TextUtils.isEmpty(message)) {
            message = getString(R.string.no_joke_found);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(message)

                .setNegativeButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        return builder.create();
    }

}