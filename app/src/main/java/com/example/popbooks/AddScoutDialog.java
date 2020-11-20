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

public class AddScoutDialog extends AppCompatDialogFragment {
public EditText addScoutName;
public Spinner addScoutRank;
private AddScoutDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_scout_dialog, null);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Add Scout",(dialogInterface, i)->{
                    String name = addScoutName.getText().toString();
                    if(name.matches("")) {
                        Toast.makeText(getContext(), "Please provide values for each field", Toast.LENGTH_SHORT).show();
                }
                        else{
                        int rank = addScoutRank.getSelectedItemPosition();
                        listener.applyTexts(name, rank);

                        }

                });
        addScoutName =view.findViewById(R.id.addscout_name);
        addScoutRank = view.findViewById(R.id.addscout_rank_spinner);
        return builder.create();


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (AddScoutDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "must implement AddScoutDialogListener");
        }
    }
    public interface AddScoutDialogListener{
        void applyTexts(String name, int rank);
    }
}
