package com.example.popbooks;

import android.content.Context;
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

public class saleInvAdapter extends ArrayAdapter<PopInv> {
    private LayoutInflater inflater;
    private ArrayList<PopInv> popinvList;
    private int resourceId;
    DBOpenHelper helper = new DBOpenHelper(getContext());

    public saleInvAdapter(Context context, int viewResourceId, ArrayList<PopInv> popList) {
        super(context, viewResourceId, popList);
        this.popinvList = popList;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resourceId = viewResourceId;

    }
    public View getView(int pos, View convertView, ViewGroup parents){
        convertView = inflater.inflate(resourceId, null);
        PopInv popinv = popinvList.get(pos);
        if (popinv != null){
            ImageView img = convertView.findViewById(R.id.sale_img);
            TextView name = convertView.findViewById(R.id.sale_name_txt);
            TextView id = convertView.findViewById(R.id.sale_id_txt);
            TextView price = convertView.findViewById(R.id.sale_price_txt);
            TextView saleId = convertView.findViewById(R.id.sale_saleid_txt);
            TextView qty = convertView.findViewById(R.id.sale_qty_txt);
            Button sell = convertView.findViewById(R.id.make_sale_btn);
            sell.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                        int popQty = 0;
                        int popSoldQty = 0;
                        int total = 0;
                        int price = 0;
                        int cents = 0;
                        Cursor popInvCursor = helper.getOnePopInv((int) popinv.getId());
                        while (popInvCursor.moveToNext()) {
                            popQty = popInvCursor.getInt(popInvCursor.getColumnIndex(DBOpenHelper.POP_INV_QTY));
                            cents = popInvCursor.getInt(popInvCursor.getColumnIndex(DBOpenHelper.POP_INV_PRICE));
                            price = (cents /100);
                        }

                        Cursor saleCursor = helper.getSale(popinv.getSaleId());
                        while (saleCursor.moveToNext()) {
                            total = saleCursor.getInt(saleCursor.getColumnIndex(DBOpenHelper.SALE_TOTAL));

                        }
                    if(popQty > 0) {
                        Cursor popSoldCursor = helper.getPopSold((int) popinv.getId(), (int) popinv.getSaleId());
                        if (popSoldCursor.moveToNext()) {
                            popSoldQty = popSoldCursor.getInt(popSoldCursor.getColumnIndex(DBOpenHelper.POP_SOLD_ITEM_QTY));
                            helper.updateSoldPop((int) popinv.getId(), (int) popinv.getSaleId(), popSoldQty + 1);
                            helper.updatePopQty(popinv.getId(), popQty - 1);
                            popQty = popQty - 1;
                            helper.addToTotal((int) popinv.getSaleId(), total + price);
                            Toast.makeText(getContext(), "New Total: $" + String.valueOf(total + price), Toast.LENGTH_SHORT).show();

                        } else {
                            helper.addToSoldPopTable(popinv);
                            helper.updatePopQty(popinv.getId(), popQty - 1);
                            popQty = popQty -1;
                            helper.addToTotal((int) popinv.getSaleId(), total + price);
                            Toast.makeText(getContext(), "New Total: $" + String.valueOf(total + price), Toast.LENGTH_SHORT).show();
                        }
                        qty.setText("#: " + String.valueOf(popQty));

                    }else{
                        Toast.makeText(getContext(), "No inventory to sell", Toast.LENGTH_SHORT).show();
                    }


                }
            });
            img.setImageResource(popinv.getImg());
            name.setText(popinv.getName());
            saleId.setText(String.valueOf(popinv.getSaleId()));
            price.setText("$ " + String.valueOf((int)popinv.getPrice()));
            id.setText( String.valueOf(popinv.getId()));
            qty.setText("#: " + String.valueOf(popinv.getQty()));
            convertView.setId((int)popinv.getId());
        }
        return convertView;
    }
}

