package com.example.popbooks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddressEntryDialog extends AppCompatDialogFragment {
    public EditText street;
    public EditText city;
    public EditText state;
    public EditText zip;
    public EditText name;
    public Spinner type;
    private AddressEntryDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.manual_address_entry_dialog, null);

        builder.setView(view)
                .setPositiveButton("use this address", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(street.getText() != null && city.getText() != null && state.getText() != null && zip.getText() != null) {
                            String address = "(" + name.getText().toString() + ")" +
                                    street.getText().toString() + " , " +
                                    city.getText().toString() + " , " +
                                    state.getText().toString() + " , " +
                                    zip.getText().toString();
                            listener.applyAddress(address, type.getSelectedItemPosition());
                        }else{
                            Toast.makeText(getContext(), "Please fill out fields.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("cancel entry", null);
        type = view.findViewById(R.id.sale_type_spinner);
        name = view.findViewById(R.id.address_entry_name);
        street = view.findViewById(R.id.address_entry_street);
        city = view.findViewById(R.id.address_entry_city);
        state = view.findViewById(R.id.address_entry_state);
        zip = view.findViewById(R.id.address_entry_zip);
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (AddressEntryDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "must implement addressEntryDialogListener");
        }
    }
    public interface AddressEntryDialogListener{
        void applyAddress(String address, int type);
    }
}
