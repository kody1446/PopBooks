package com.example.popbooks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditPopInvDialog extends AppCompatDialogFragment {
    public EditText editName;
    public EditText editDesc;
    public EditText editPrice;
    public EditText editQty;
    public PopInv pop;
    private EditPopInvDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_popinv_dialog, null);

        builder.setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Apply Changes", (dialogInterface, i)-> {
                    String name = editName.getText().toString();
                    String desc = editDesc.getText().toString();
                    double price = Double.parseDouble(editPrice.getText().toString());
                    int qty = Integer.parseInt(editQty.getText().toString());
                    if(name != null && desc != null){
                        listener.applyTexts(name, desc, (int)price, qty);
                    }else{
                        Toast.makeText(getActivity(), "Please fill out the required fields", Toast.LENGTH_SHORT).show();
                    }
                });
        editName = view.findViewById(R.id.edit_prod_name);
        editDesc = view.findViewById(R.id.edit_prod_desc);
        editPrice = view.findViewById(R.id.edit_prod_price);
        editQty = view.findViewById(R.id.edit_prod_qty);
        editName.setText(pop.getName());
        editDesc.setText(pop.getDesc());
        editPrice.setText(String.valueOf(pop.getPrice()));
        editQty.setText(String.valueOf(pop.getQty()));
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (EditPopInvDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "must implement AddScoutDialogListener");
        }
    }
    public void setDialogValues(PopInv p){
        pop = p;

    }
    public interface EditPopInvDialogListener{
        void applyTexts(String name, String desc, int price, int qty);
    }
}
