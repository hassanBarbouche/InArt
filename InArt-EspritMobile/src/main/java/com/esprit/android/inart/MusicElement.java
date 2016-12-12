package com.esprit.android.inart;

import com.parse.ParseFile;

/**
 * Created by Mariem on 17/11/2015.
 */
public class MusicElement {
    public String name;
    public String description;
    public ParseFile photo_music;
    public ParseFile music_element;
    public String url_photo;
    public String Owner;
    public long mId;
    public int likes;
    // public Integer note;
    // public Integer nbr_signaux;

    public MusicElement(String name, String description, ParseFile photo_music, String Url, long MII, String own,ParseFile music_element) {
        this.name = name;
        this.description = description;
        this.photo_music = photo_music;
        this.url_photo = Url;
        this.mId=MII;
        this.Owner=own;
        this.music_element=music_element;
        //this.note = note;
        // this.nbr_signaux = nbr_signaux;
    }
    public MusicElement(String name, String description, ParseFile music_element, String Url, String own,String url_photo) {
        this.name = name;
        this.description = description;
        this.music_element = music_element;
        this.url_photo = Url;

        this.Owner=own;
        this.url_photo=url_photo;

        this.music_element=music_element;
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
