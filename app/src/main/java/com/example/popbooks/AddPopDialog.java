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

public class AddPopDialog extends AppCompatDialogFragment {
    public EditText addName;
    public EditText addDesc;
    public EditText addPrice;
    public EditText addQty;
    private AddPopDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_pop_dialog, null);

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add Pop", (dialogInterface, i)->{
                    String name = addName.getText().toString();
                    String desc = addDesc.getText().toString();
                    double price = Double.valueOf(addPrice.getText().toString());
                    int qty = Integer.valueOf(addQty.getText().toString());
                    if(name == null || desc == null || price == 0.0 || qty == 00){
                        Toast.makeText(getContext(), "Please fill out all fields before submitting", Toast.LENGTH_SHORT).show();
                    }else{
                        listener.applyTexts(name, desc, price, qty);
                    }
                });
        addName = view.findViewById(R.id.add_pop_name);
        addDesc = view.findViewById(R.id.add_pop_desc);
        addPrice = view.findViewById(R.id.add_pop_price);
        addQty = view.findViewById(R.id.add_pop_qty_dialog);
        return builder.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (AddPopDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "must implement AddScoutDialogListener");
        }
    }
    public interface AddPopDialogListener{
        void applyTexts(String n, String d, double p, int q);
    }
}
