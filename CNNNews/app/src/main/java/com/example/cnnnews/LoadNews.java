package com.example.cnnnews;

import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Allen on 2/13/17.
 */

public class LoadNews extends AsyncTask<String,Void,ArrayList<NewsItem>> {
    MainActivity mainActivity;
    public LoadNews(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    ArrayList<NewsItem> newsItems;
    @Override
    protected void onPostExecute(ArrayList<NewsItem> newsItems) {
        super.onPostExecute(newsItems);
        mainActivity.passData(newsItems);
        Log.d("Retrived",newsItems.toString());
    }

    @Override
    protected ArrayList<NewsItem> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = httpURLConnection.getInputStream();
                newsItems=NewsItemsUtil.NewsItemsSAXParser.parseNewsItemUsingSAX(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return newsItems;
    }
    public interface passDataInterface{
        public void passData(ArrayList<NewsItem> newsItems);
    }
}
