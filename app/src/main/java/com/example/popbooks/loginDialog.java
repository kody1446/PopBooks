package com.example.popbooks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class loginDialog extends AppCompatDialogFragment {
    public EditText pinTxt;
    public static final int pin = 1234;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_dialog, null);

        builder.setView(view)
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);

                    }
                })
                .setPositiveButton("login",(dialogInterface, i)->{
                    int enteredPin = Integer.valueOf(pinTxt.getText().toString());
                    if(enteredPin == pin){
                        dismiss();
                    }else{
                        Toast.makeText(getContext(), "That is the incorrect pin. Please try again.", Toast.LENGTH_SHORT).show();
                        getActivity().recreate();
                        dismiss();

                    }


                });
        pinTxt = view.findViewById(R.id.pin_edittxt);
        return builder.create();
    }
}
