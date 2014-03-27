package com.example.gettogether;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapter extends BaseAdapter {
	ArrayList<String> strings;
	ArrayList<Bitmap> bitmaps;
	Context context;
    private static LayoutInflater inflater=null;

	public CustomAdapter(main_activity main_activity, ArrayList<String> prgmNameList, ArrayList<Bitmap> prgmImages) {
		// TODO Auto-generated constructor stub
		strings =prgmNameList;
		context =main_activity;
		bitmaps =prgmImages;
		inflater = ( LayoutInflater )context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	 public class Holder
	    {
	        TextView tv;
	        ImageView img;
	    }
	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	        Holder holder=new Holder();
	        View rowView;        
	             rowView = inflater.inflate(R.layout.program_list, null);
	             holder.tv=(TextView) rowView.findViewById(R.id.textView1);
	             holder.img=(ImageView) rowView.findViewById(R.id.imageView1);       
	         holder.tv.setText(strings.get(position));
	         holder.img.setImageBitmap(bitmaps.get(position));
	         rowView.setOnClickListener(new OnClickListener() {         
	            @Override
	            public void onClick(View v) {
	                // TODO Auto-generated method stub
	                Toast.makeText(context, "You Clicked "+strings.get(position), Toast.LENGTH_LONG).show();
	            }
	        });  
	        return rowView;
	    }


}
