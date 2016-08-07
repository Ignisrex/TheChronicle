package com.example.android.thechronicle;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by keane on 8/1/2016.
 */
public class ArticleLoader extends AsyncTaskLoader {
    private String mUrl;


    public ArticleLoader(Context context,String url){
        super(context);
        mUrl= url;
    }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null){
            return null;
        }

        URL url = QueryUtils.createUrl(mUrl);
        String jsonResponse = "";
        try{
            jsonResponse=QueryUtils.makeHttpRequest(url);
        }catch (IOException e){
            Log.e("ArticleLoader Class","Error making request");
        }

        List<Article> articles = QueryUtils.extractArticle(jsonResponse);
        return articles;
    }
}
