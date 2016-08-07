package com.example.android.thechronicle;

/**
 * Created by keane on 8/1/2016.
 */
public class Article {
    private String mTitle;
    private String mDate;
    private String mUrl;

    public Article(String t, String d, String u){
        mTitle = t;
        mDate = d;
        mUrl = u;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
