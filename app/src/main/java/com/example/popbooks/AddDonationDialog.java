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

public class AddDonationDialog extends AppCompatDialogFragment {
    public EditText addDonationAmount;
    private AddDonationDialog.AddDonationDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_donation_dialog, null);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add Donation",(dialogInterface, i)->{
                    if(!addDonationAmount.getText().toString().equals("")) {
                        int amount = Integer.valueOf(addDonationAmount.getText().toString());
                        if (amount <= 0) {
                            Toast.makeText(getContext(), "Please provide values for each field", Toast.LENGTH_SHORT).show();
                        } else {
                            listener.applyDonation(amount);
                            Toast.makeText(getContext(), "Donation Added", Toast.LENGTH_SHORT).show();

                        }
                    }else{
                        Toast.makeText(getContext(), "Please provide values for each field", Toast.LENGTH_SHORT).show();
                    }

                });
        addDonationAmount = view.findViewById(R.id.add_donation_amount);
        return builder.create();


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (AddDonationDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "must implement AddScoutDialogListener");
        }
    }
    public interface AddDonationDialogListener{
        void applyDonation(int amount);
    }
}
