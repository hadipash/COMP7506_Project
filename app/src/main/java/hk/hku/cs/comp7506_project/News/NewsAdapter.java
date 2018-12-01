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
import androidx.recyclerview.widget.RecyclerView;
import hk.hku.cs.comp7506_project.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static final String TAG = "NewsAdapter";
    private int num = 0;
    private static JSONArray news_data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "News Adapter";
        public final TextView title;
        public final TextView content;
        public final TextView source;
        public final ImageView pic;

        public ViewHolder(View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), NewsPage.class);
                    try {
                        intent.putExtra("news_id", news_data.getJSONObject(getAdapterPosition()).getInt("ID"));
                    } catch (JSONException e) {
                        Log.e(TAG, "Extracting news_id", e);
                    }
                    v.getContext().startActivity(intent);
                }
            });

            title = v.findViewById(R.id.news_title);
            content = v.findViewById(R.id.news_content);
            source = v.findViewById(R.id.news_source);
            pic = v.findViewById(R.id.news_img);
        }
    }

    NewsAdapter(JSONObject data){
        try {
            num = data.getInt("news_num");
            news_data = data.getJSONArray("news");
        } catch (JSONException e) {
            Log.e(TAG, "Extracting data from JSON", e);
        }
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.news_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        try {
            Picasso.get().load(news_data.getJSONObject(position).getString("s_pic")).into(holder.pic);
            holder.title.setText(news_data.getJSONObject(position).getString("s_topic"));
            holder.content.setText(news_data.getJSONObject(position).getString("brief_content"));
            holder.source.setText(news_data.getJSONObject(position).getString("source"));
        } catch (JSONException e) {
            Log.e(TAG, "Extracting data from JSON", e);
        }
    }

    @Override
    public int getItemCount() {
        return num;
    }
}
