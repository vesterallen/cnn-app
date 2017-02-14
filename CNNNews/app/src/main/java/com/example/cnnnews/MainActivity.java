package com.example.cnnnews;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadNews.passDataInterface,GetImage.GetPicture{
    ImageButton next;
    ImageButton last;
    ImageButton previous;
    ImageButton first;
    ImageView imageView;
    Button getNews;
    ArrayList<NewsItem> newsItems;
    LinearLayout linearLayout;
    TextView title;
    TextView description;
    TextView date;
    int index=0;
    Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout= (LinearLayout) findViewById(R.id.linearLayout);
        newsItems=new ArrayList<NewsItem>();
        first= (ImageButton) findViewById(R.id.first);
        last= (ImageButton) findViewById(R.id.last);
        previous= (ImageButton) findViewById(R.id.previous);
        next= (ImageButton) findViewById(R.id.next);
        imageView= (ImageView) findViewById(R.id.imageView);
        getNews= (Button) findViewById(R.id.getNews);
        finish= (Button) findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index==newsItems.size()-1){

                }else {
                    index++;
                    displayContents(index);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index==0){

                }else {
                    index--;
                    displayContents(index);
                }
            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index==newsItems.size()-1){

                }else {
                    index=newsItems.size()-1;
                    displayContents(index);
                }
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index==0){

                }else {
                    index=0;
                    displayContents(index);
                }
            }
        });
        getNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadNews(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_tech.rss");
            }
        });

    }

    @Override
    public void passData(ArrayList<NewsItem> newsItems) {
        Log.d("sizeeeee", newsItems.size()+"");
        this.newsItems=newsItems;
        displayContents(0);

    }
    public void displayContents(int index){
        //Log.d("tetstt", (newsItems.get(index)).getImageLink());
        new GetImage(MainActivity.this).execute((newsItems.get(index)).getImageLink());
        linearLayout.removeAllViews();
        title=new TextView(MainActivity.this);
        description=new TextView(MainActivity.this);
        date=new TextView(MainActivity.this);
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        title.setLayoutParams(params);
        description.setLayoutParams(params);
        date.setLayoutParams(params);
        title.setText(newsItems.get(index).getTitle());
        description.setText("Description:\n"+newsItems.get(index).getDescription());
        date.setText("Published on:"+newsItems.get(index).getPubDate());
        linearLayout.addView(title);
        linearLayout.addView(date);
        linearLayout.addView(description);
    }

    @Override
    public void getPicturefr(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
