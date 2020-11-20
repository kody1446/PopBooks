package com.example.popbooks;



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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditScoutDialog extends AppCompatDialogFragment {
    public EditText scoutName;
    public Spinner scoutRank;
    public long scoutId;
    public String scoutNameSt;
    public int scoutRankin;
    private EditScoutDialogListener listener;
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_scout_dialog, null);

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Apply Changes", (dialogInterface, i)-> {
                    String name = scoutName.getText().toString();
                    int rank = scoutRank.getSelectedItemPosition();
                    if(name.matches("")) {
                        Toast.makeText(getContext(), "Please provide values for each field", Toast.LENGTH_SHORT).show();

                    }else{
                        listener.applyTexts(name, rank,(int) scoutId);
                    }
                });
        scoutName = view.findViewById(R.id.edit_scout_name);
        scoutRank = view.findViewById(R.id.edit_scout_rank);
        scoutName.setText(scoutNameSt);
        scoutRank.setSelection(scoutRankin);
        return builder.create();

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (EditScoutDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "must implement AddScoutDialogListener");
        }
    }
    public void setDialogValues(String name, int rank, long id){
        scoutNameSt = name;
        scoutRankin = rank;
        scoutId = id;
    }
    public interface EditScoutDialogListener{
        void applyTexts(String name, int rank, int id);

    }

}
