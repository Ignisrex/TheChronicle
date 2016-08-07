package com.example.android.thechronicle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by keane on 8/1/2016.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(Context context, List<Article> articles){
        super(context,0,articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listViewItem = convertView;
        if(listViewItem == null ){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.article_list_item,parent,false);
        }

        Article currentArticle = getItem(position);
        TextView titleView = (TextView) listViewItem.findViewById(R.id.title);
        titleView.setText(currentArticle.getTitle());

        TextView dateView = (TextView) listViewItem.findViewById(R.id.date);
        dateView.setText(currentArticle.getDate());

        return listViewItem;
    }
}
