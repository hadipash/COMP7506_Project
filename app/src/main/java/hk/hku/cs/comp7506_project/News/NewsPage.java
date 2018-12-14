package hk.hku.cs.comp7506_project.News;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import hk.hku.cs.comp7506_project.JSONRequest;
import hk.hku.cs.comp7506_project.R;
import hk.hku.cs.comp7506_project.Wiki.WikiPage;

public class NewsPage extends AppCompatActivity {
    private static final String TAG = "NewsPage";
    private final static String URL = "https://i.cs.hku.hk/~rustam/news.php";
    private final static String TOKEN = "@@@@";

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
            setText(content, news.getString("content"));
            Picasso.get().load(news.getString("pic")).into(pic);
        } catch (ExecutionException | InterruptedException | JSONException e) {
            Log.e(TAG, "Extracting news details", e);
        }
    }

    private void setText(TextView textView, String content){
        final WikiPage wiki = new WikiPage();
        final String[] parsed = content.split(TOKEN);
        boolean clickable = false;
        SpannableStringBuilder text = new SpannableStringBuilder();

        for (String aParsed : parsed){
            if (clickable) {
                final String word = aParsed;

                SpannableString link = new SpannableString(word);
                link.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        wiki.popItUp(word, widget.getContext(), getWindow().getDecorView().getRootView());
                    }
                },0, word.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                text.append(link);
                clickable = false;
            }
            else {
                text.append(aParsed);
                clickable = true;
            }
        }

        textView.setText(text);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
