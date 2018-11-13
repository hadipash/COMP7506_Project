package hk.hku.cs.comp7506_project.News;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import hk.hku.cs.comp7506_project.R;

public class NewsPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        NewsObject news = getIntent().getParcelableExtra("data");

        TextView title = findViewById(R.id.news_title_detailed);
        TextView content = findViewById(R.id.news_content_detailed);
        ImageView pic = findViewById(R.id.news_img_detailed);

        title.setText(news.getTitle());
        content.setText(news.getContent());
        pic.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img1));
    }
}
