package com.example.gettogether;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SuggestionAdapter extends ArrayAdapter<Suggestion>{

    Context context; 
    int layoutResourceId;    
    ArrayList<Suggestion> data = null;
    
    public SuggestionAdapter(Context context, int layoutResourceId, ArrayList<Suggestion> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SuggestionHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new SuggestionHolder();
            holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            
            row.setTag(holder);
        }
        else
        {
            holder = (SuggestionHolder)row.getTag();
        }
        
        Suggestion weather = data.get(position);
        holder.txtTitle.setText(weather.title);
        holder.imgIcon.setImageBitmap(weather.icon);
        
        return row;
    }
    
    static class SuggestionHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }
}