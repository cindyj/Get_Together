package com.example.gettogether;

import android.graphics.Bitmap;

public class Suggestion {
    public Bitmap icon;
    public String title;
    public Suggestion(){
        super();
    }
    
    public Suggestion(Bitmap icon, String title) {
        super();
        this.icon = icon;
        this.title = title;
    }
    
    public String getTitle(){
    	return title;
    }
}