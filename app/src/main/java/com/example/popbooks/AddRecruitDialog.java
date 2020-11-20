package com.example.popbooks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddRecruitDialog extends AppCompatDialogFragment {
    public EditText addRecruitName;
    public EditText addRecruitPhone;
    private AddRecruitDialog.AddRecruitDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_recruit_dialog, null);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add Scout",(dialogInterface, i)->{
                    String name = addRecruitName.getText().toString();
                    String phone = addRecruitPhone.getText().toString();
                    if(name.matches("") || phone.matches("")) {
                        Toast.makeText(getContext(), "Please provide values for each field", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        listener.applyRecruit(name, phone);

                    }

                });
        addRecruitName = view.findViewById(R.id.add_recruit_name);
        addRecruitPhone = view.findViewById(R.id.add_recruit_phone);
        return builder.create();


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (AddRecruitDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "must implement AddScoutDialogListener");
        }
    }
    public interface AddRecruitDialogListener{
        void applyRecruit(String name, String phone);
    }
}
