package hk.hku.cs.comp7506_project.News;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import hk.hku.cs.comp7506_project.JSONRequest;
import hk.hku.cs.comp7506_project.R;

public class NewsFeedFragment extends Fragment {
    private static final String TAG = "NewsFeedFragment";
    private final static String URL = "https://i.cs.hku.hk/~rustam/news.php";
    private final static int TOPNEWSNUM = 3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_news, container, false);

        final ViewPager mViewPager = rootView.findViewById(R.id.top_news);
        mViewPager.setOffscreenPageLimit(TOPNEWSNUM - 1);
        Map<String, String> top_news_params = new HashMap<>();
        top_news_params.put("url", URL);
        top_news_params.put("action", "top_news");
        try {
            mViewPager.setAdapter(new TopNewsAdapter(new JSONRequest().execute(top_news_params).get()));

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % TOPNEWSNUM, true);
                    handler.postDelayed(this, 4000);
                }
            }, 4000);
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "Setting top_news", e);
        }

        RecyclerView mRecyclerView = rootView.findViewById(R.id.news_recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        Map<String, String> news_feed_params = new HashMap<>();
        news_feed_params.put("url", URL);
        news_feed_params.put("action", "news_feed");
        try {
            mRecyclerView.setAdapter(new NewsAdapter(new JSONRequest().execute(news_feed_params).get()));
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "Setting news_feed", e);
        }

        return rootView;
    }
}
