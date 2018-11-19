package hk.hku.cs.comp7506_project.Forum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hk.hku.cs.comp7506_project.R;


public class ForumFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_forum, container, false);

        mRecyclerView = rootView.findViewById(R.id.post);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ForumAdapter(getPost());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private ForumPost[] getPost() {
        ForumPost postexample[] = new ForumPost[3];

        postexample[0] = new ForumPost("hahahahha", "ahfdjakhdfjashdfk");

        postexample[1] = new ForumPost("hahahahhaasdfasf", "ahfdjakhdfja1234shdfk");

        postexample[2] = new ForumPost("hahahahha12341234", "ahfdjakhd6453645fjashdfk");

        return postexample;
    }
}
