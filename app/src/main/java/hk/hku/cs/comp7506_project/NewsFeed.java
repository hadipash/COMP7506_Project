package hk.hku.cs.comp7506_project;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class NewsFeed extends Activity {
    private RecyclerView news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        news = findViewById(R.id.news_recycle_view);
        news.setHasFixedSize(true);

    }
}
