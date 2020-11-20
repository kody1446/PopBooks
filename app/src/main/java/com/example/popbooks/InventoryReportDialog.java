package com.example.popbooks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class InventoryReportDialog extends AppCompatDialogFragment {
    public EditText invReport;
    public Button shareReport;
    public TextView title;
    public String dialogTitleStr;
    public String shareReportMsg;
    public String scoutName;
    public String report;
    public Date currentDate;
    private InventoryReportDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        currentDate = Calendar.getInstance().getTime();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.inventory_report_dialog, null);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        invReport = view.findViewById(R.id.inv_report_edtxt);
        invReport.setText(report);
        title = view.findViewById(R.id.inv_report_title);
        title.setText(dialogTitleStr);
        shareReport = view.findViewById(R.id.share_report_btn);
        shareReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareReportMsg = dialogTitleStr + "\n"+
                        "SCOUT NAME: " + scoutName + "\n" + "\n" +"\n" +
                        report;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareReportMsg);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        });

        return builder.create();


    }
    public void setInvDialogValues(String name, String invReport, String title){
        scoutName = name;
        report = invReport;
        dialogTitleStr = title;

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (InventoryReportDialogListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "must implement AddScoutDialogListener");
        }
    }
    public interface InventoryReportDialogListener{

    }



}
