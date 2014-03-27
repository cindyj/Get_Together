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

public class ContactObjAdapter extends ArrayAdapter<ContactObj>{

    Context context; 
    int layoutResourceId;    
    ArrayList<ContactObj> data = null;
    
    public ContactObjAdapter(Context context, int layoutResourceId, ArrayList<ContactObj> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ContactObjHolder holder = null;
        
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            
            holder = new ContactObjHolder();
            holder.tvName = (TextView)row.findViewById(R.id.cBContactName);
            holder.tvNumber = (TextView)row.findViewById(R.id.tVContactNumber);
            
            row.setTag(holder);
        }
        else
        {
            holder = (ContactObjHolder)row.getTag();
        }
        
        ContactObj weather = data.get(position);
        holder.tvName.setText(weather.name);
        holder.tvNumber.setText(weather.number);
        
        return row;
    }
    
    static class ContactObjHolder
    {
        TextView tvName;
        TextView tvNumber;
    }
}