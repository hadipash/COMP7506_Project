package hk.hku.cs.comp7506_project.News;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import hk.hku.cs.comp7506_project.R;

public class TopNewsAdapter extends PagerAdapter {
    private static final String TAG = "TopNewsAdapter";
    private int num = 0;
    private static JSONArray top_news_data;

    TopNewsAdapter(JSONObject data){
        try {
            num = data.getInt("news_num");
            top_news_data = data.getJSONArray("news");
        } catch (JSONException | NullPointerException e) {
            Log.e(TAG, "Extracting news details", e);
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.top_news_card, container, false);

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewsPage.class);
                try {
                    intent.putExtra("news_id", top_news_data.getJSONObject(position).getInt("ID"));
                } catch (JSONException e) {
                    Log.e(TAG, "Extracting news_id", e);
                }
                v.getContext().startActivity(intent);
            }
        } );

        ImageView img = view.findViewById(R.id.top_news_img);
        TextView title = view.findViewById(R.id.top_news_title);
        TextView source = view.findViewById(R.id.top_news_source);

        try {
            Picasso.get().load(top_news_data.getJSONObject(position).getString("pic")).into(img);
            title.setText(top_news_data.getJSONObject(position).getString("s_topic"));
            source.setText(top_news_data.getJSONObject(position).getString("source"));
        } catch (JSONException e) {
            Log.e(TAG, "Extracting news details", e);
        }

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return num;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }
}
