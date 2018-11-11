package hk.hku.cs.comp7506_project.Forum;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hk.hku.cs.comp7506_project.R;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder> {

    private ForumPost[] mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ForumAdapter(ForumPost[] myDataset) {
        mDataset = myDataset;
    }

    public static class ForumViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public final TextView postTitle;
        public final TextView postContent;
        public ForumViewHolder(View v) {
            super(v);
            postTitle = v.findViewById(R.id.post_title);
            postContent = v.findViewById(R.id.post_content);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ForumAdapter.ForumViewHolder  onCreateViewHolder(@NonNull ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_forum_card, parent, false);

        ForumViewHolder vh = new ForumViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Log.d("post", "title:" + mDataset[position].getPostTitle() + " \n");
        Log.d("post", "Content: " + mDataset[position].getPostContent() + " \n");
        holder.postTitle.setText(mDataset[position].getPostTitle());
        holder.postContent.setText(mDataset[position].getPostContent());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
