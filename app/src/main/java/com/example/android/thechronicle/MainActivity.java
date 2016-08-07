package com.example.android.thechronicle;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Article>> {

    private static final String USG_REQUEST_URL = "http://content.guardianapis.com/search?show-tags=contributor&q=politics&api-key=0db44f87-fdf0-4300-8e5b-655f2026c5b7";
    private static final int ARTICLE_LOADER_ID =1;
    private ArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView articlesListView = (ListView) findViewById(R.id.list);
        mAdapter =new ArticleAdapter(this,new ArrayList<Article>());

        articlesListView.setAdapter(mAdapter);

        articlesListView.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Article currentArticle = mAdapter.getItem(position);
                Uri currentArticleUri = Uri.parse(currentArticle.getUrl());
                Intent articleIntent =  new Intent(Intent.ACTION_VIEW,currentArticleUri);
                startActivity(articleIntent);
            }
        });

        android.app.LoaderManager loaderManager  = getLoaderManager();
        loaderManager.initLoader(ARTICLE_LOADER_ID,null,this);
    }

    @Override
    public android.content.Loader<List<Article>> onCreateLoader(int id, Bundle args) {
        return new ArticleLoader(this,USG_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Article>> loader, List<Article> articles) {
        mAdapter.clear();
        if (articles != null && !articles.isEmpty()){
            mAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Article>> loader) {
        mAdapter.clear();
    }
}
