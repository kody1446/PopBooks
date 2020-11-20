package com.example.popbooks;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class scoutAdapter extends ArrayAdapter<Scout> {
    private LayoutInflater inflater;
    private ArrayList<Scout> scouts;
    private int resourceId;
    public scoutAdapter(Context context, int viewResourceId, ArrayList<Scout> scoutlist){
        super(context, viewResourceId, scoutlist);
        this.scouts = scoutlist;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resourceId = viewResourceId;
    }
    public View getView(int pos, View convertView, ViewGroup parents){
        convertView = inflater.inflate(resourceId, null);
        Scout scout = scouts.get(pos);
        if (scout != null){
            ImageView img = convertView.findViewById(R.id.image_view_ss);
            TextView name = convertView.findViewById(R.id.scout_name);
            TextView id = convertView.findViewById(R.id.scout_id);
            img.setImageResource(scout.getImg());
            name.setText(scout.getName());
            String scoutId = Long.toString(scout.getId());
            id.setText(scoutId);
            convertView.setId((int)scout.getId());
        }
        return convertView;
    }
}
