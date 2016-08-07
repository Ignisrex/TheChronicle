package com.example.android.thechronicle;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by keane on 8/1/2016.
 */
public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String STRING_SEPERATOR = "T";
    public QueryUtils(){
    }

    private static String formatDate(String s){
        String[] strings = s.split(STRING_SEPERATOR);

        return strings[0];
    }
    public static ArrayList<Article> extractArticle(String jsonResponse){
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        ArrayList<Article> articles = new ArrayList<>();

        try{
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i =0; i<results.length();i++){

                JSONObject currentArticle = results.getJSONObject(i);
                String title =  currentArticle.getString("webTitle");
                String date = formatDate(currentArticle.getString("webPublicationDate"));
                String url = currentArticle.getString("webUrl");
                articles.add(new Article(title,date,url));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON results");
        }
        return articles;
    }
    public static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output= new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection =null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e){
            Log.e(LOG_TAG,"I/O exception while trying to read from server");
            return null;
        }
        return jsonResponse;
    }

    public static URL createUrl(String stringUrl){

        URL url =null;
        try {
            url = new URL(stringUrl);

        }catch (MalformedURLException exception){
            Log.e(LOG_TAG,"Error creating url",exception);
        }
        return url;
    }
}
