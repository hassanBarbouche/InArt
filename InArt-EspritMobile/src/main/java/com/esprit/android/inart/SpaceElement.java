package com.esprit.android.inart;

import com.parse.ParseFile;

/**
 * Created by Mariem on 17/11/2015.
 */
public class SpaceElement {
    public String name;
    public String description;
    public ParseFile photo_element;
    public String url;
    public String Owner;
    public String adress;
    public String genre;
    public long mId;
    public int likes;
    // public Integer note;
    // public Integer nbr_signaux;

    public SpaceElement(String name, String description, ParseFile photo_element, String Url, long MII, String own) {
        this.name = name;
        this.description = description;
        this.photo_element = photo_element;
        this.url = Url;
        this.mId=MII;
        this.Owner=own;
        //this.note = note;
        // this.nbr_signaux = nbr_signaux;
    }
    public SpaceElement(String name, String description, ParseFile photo_element, String Url, String own, String add,String gen) {
        this.name = name;
        this.description = description;
        this.photo_element = photo_element;
        this.url = Url;
        this.adress=add;
        this.genre=gen;
        this.Owner=own;

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
