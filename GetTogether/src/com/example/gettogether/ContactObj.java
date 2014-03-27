package com.example.gettogether;


public class ContactObj {
    public String name;
    public String number;
    public ContactObj(){
        super();
    }
    
    public ContactObj(String name, String number) {
        super();
        this.name = name;
        this.number = number;
    }
    
    public String getName(){
    	return name;
    }
    
    public String getNumber(){
    	return number;
    }
}