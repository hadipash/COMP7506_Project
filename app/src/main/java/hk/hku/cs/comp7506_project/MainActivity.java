package hk.hku.cs.comp7506_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import hk.hku.cs.comp7506_project.Forum.Forum;
import hk.hku.cs.comp7506_project.News.NewsFeed;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OpenNewsFeed(View view) {
        Intent intent = new Intent(this, NewsFeed.class);
        startActivity(intent);
    }

    public void openForum(View view)
    {
        Intent intent2 = new Intent(this, Forum.class);
        startActivity(intent2);
    }

}
