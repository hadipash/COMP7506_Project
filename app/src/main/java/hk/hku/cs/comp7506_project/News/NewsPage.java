package hk.hku.cs.comp7506_project.News;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;
import hk.hku.cs.comp7506_project.JSONRequest;
import hk.hku.cs.comp7506_project.R;

public class NewsPage extends AppCompatActivity {
    private static final String TAG = "NewsPage";
    private final static String URL = "https://i.cs.hku.hk/~rustam/news.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);

        TextView title = findViewById(R.id.news_title_detailed);
        TextView content = findViewById(R.id.news_content_detailed);
        ImageView pic = findViewById(R.id.news_img_detailed);

        int news_id = getIntent().getIntExtra("news_id", 1);
        Map<String, String> params = new HashMap<>();
        params.put("url", URL);
        params.put("action", "news");
        params.put("id", Integer.toString(news_id));

        try {
            JSONObject news = new JSONRequest().execute(params).get();
            title.setText(news.getString("topic"));
            content.setText(news.getString("content"));
            Picasso.get().load(news.getString("pic")).into(pic);
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.e(TAG, "Extracting news details", e);
        }
    }
}
