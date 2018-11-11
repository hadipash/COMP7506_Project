package hk.hku.cs.comp7506_project.Forum;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hk.hku.cs.comp7506_project.R;


public class Forum extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        mRecyclerView = (RecyclerView) findViewById(R.id.post);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ForumAdapter(getPost());
        mRecyclerView.setAdapter(mAdapter);
    }

    private ForumPost[] getPost()
    {
        ForumPost postexample[] = new ForumPost[3];

        postexample[0] = new ForumPost("hahahahha", "ahfdjakhdfjashdfk");

        postexample[1] = new ForumPost("hahahahhaasdfasf", "ahfdjakhdfja1234shdfk");

        postexample[2] = new ForumPost("hahahahha12341234", "ahfdjakhd6453645fjashdfk");

        return postexample;
    }
}
