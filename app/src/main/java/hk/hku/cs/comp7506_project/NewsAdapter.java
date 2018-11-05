package hk.hku.cs.comp7506_project;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private static final String TAG = "NewsAdapter";
    private NewsObject[] data;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        public final TextView content;
        public final ImageView pic;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            title = v.findViewById(R.id.news_title);
            content = v.findViewById(R.id.news_content);
            pic = v.findViewById(R.id.news_img);
        }
    }

    NewsAdapter(NewsObject[] dataSet){
        data = dataSet;
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
        holder.title.setText(data[position].getTitle());
        holder.content.setText(data[position].getContent());
//        holder.pic.setImageBitmap(data[position].getPic());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
