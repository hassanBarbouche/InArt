package com.esprit.android.inart;

/**
 * Created by Mariem on 01/12/2015.
 */
public class News {
    private int id;
    private String title;
    private String content;
    private String description;


    public News(String title, String content, String description) {
        this.title = title;
        this.content = content;
        this.description=description;

    }

    public String getTitle() {
        return (title);
    }
    public int getId() {
        return (id);
    }

    public String getContent() {
        return (content);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public News() {
    }
}
