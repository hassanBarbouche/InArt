package com.esprit.android.inart;

import com.parse.ParseFile;

/**
 * Created by Mariem on 17/11/2015.
 */
public class EventElement {
    public String name;
    public String description;
    public ParseFile photo_element;
    public String url;
    public String Owner;
    public String adress;
    public String date;
    public long mId;
    public int likes;
    // public Integer note;
    // public Integer nbr_signaux;

    public EventElement(String name, String description, ParseFile photo_element, String Url, long MII, String own,String adr,String dat) {
        this.name = name;
        this.description = description;
        this.photo_element = photo_element;
        this.url = Url;
        this.mId=MII;
        this.Owner=own;
        this.adress=adr;
        this.date=dat;
        //this.note = note;
        // this.nbr_signaux = nbr_signaux;
    }
    public EventElement(String name, String description, ParseFile photo_element, String Url, String own,String adr,String dat) {
        this.name = name;
        this.description = description;
        this.photo_element = photo_element;
        this.url = Url;
        this.Owner=own;
        this.adress=adr;
        this.date=dat;
        //this.note = note;
        // this.nbr_signaux = nbr_signaux;
    }
    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return name;
    }
}
