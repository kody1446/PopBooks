package com.example.popbooks;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class popInvAdapter extends ArrayAdapter<PopInv> {
    private LayoutInflater inflater;
    private ArrayList<PopInv> popinvList;
    private int resourceId;
    DBOpenHelper helper = new DBOpenHelper(getContext());

    public popInvAdapter(Context context, int viewResourceId, ArrayList<PopInv> popList) {
        super(context, viewResourceId, popList);
        this.popinvList = popList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resourceId = viewResourceId;

    }
    public View getView(int pos, View convertView, ViewGroup parents){
        convertView = inflater.inflate(resourceId, null);
        PopInv popinv = popinvList.get(pos);
        if (popinv != null){
            ImageView img = convertView.findViewById(R.id.pop_imageview);
            TextView name = convertView.findViewById(R.id.pop_name_textview);
            TextView id = convertView.findViewById(R.id.pop_inv_id);
            TextView qty = convertView.findViewById(R.id.pop_qty_textview);
            Button plus = convertView.findViewById(R.id.add_pop_qty);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = helper.getOnePopInv((int)popinv.getId());
                    int popqty = 0;
                    while(cursor.moveToNext()){
                        popqty = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_QTY));
                    }
                    int newQty = 0;
                    newQty = popqty +1;
                    helper.updatePopQty(popinv.getId(), newQty);
                    qty.setText("#: " + String.valueOf(newQty));
                }

            });
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PopInvDetail.class);
                    intent.putExtra("popInvId", popinv.getId());
                    intent.putExtra("scoutId", popinv.getScoutId());
                    getContext().startActivity(intent);
                }
            });
            Button minus = convertView.findViewById(R.id.sub_pop_qty);
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Cursor cursor = helper.getOnePopInv((int)popinv.getId());
                   int popqty = 0;
                   while(cursor.moveToNext()){
                       popqty = cursor.getInt(cursor.getColumnIndex(DBOpenHelper.POP_INV_QTY));
                   }
                   if(popqty > 0){
                       int newQty = 0;
                       newQty = popqty -1;
                       helper.updatePopQty(popinv.getId(), newQty);
                       qty.setText("#: " + String.valueOf(newQty));

                   }else{
                       Toast.makeText(getContext(), "Cannot reduce qty below 0", Toast.LENGTH_SHORT).show();
                   }

                }
            });
            img.setImageResource(popinv.getImg());
            name.setText(popinv.getName());
            id.setText("#: " + String.valueOf(popinv.getId()));
            qty.setText("#: " + String.valueOf(popinv.getQty()));
            convertView.setId((int)popinv.getId());
        }
        return convertView;
    }
}
