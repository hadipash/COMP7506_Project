package hk.hku.cs.comp7506_project.Forum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import hk.hku.cs.comp7506_project.Forum.adapter.PostAdapter;
import hk.hku.cs.comp7506_project.Forum.application.CarbonForumApplication;
import hk.hku.cs.comp7506_project.Forum.config.APIAddress;
import hk.hku.cs.comp7506_project.Forum.util.HttpUtil;
import hk.hku.cs.comp7506_project.Forum.util.JSONUtil;
import hk.hku.cs.comp7506_project.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsActivity extends AppCompatActivity{

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        ImageButton imageButton = (ImageButton) toolbar.findViewById(R.id.notifications_settings_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotificationsActivity.this, SettingsActivity.class);
                intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, SettingsActivity.NotificationPreferenceFragment.class.getName());
                intent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return  getString(R.string.notifications_mentioned_me);
                case 1:
                    return getString(R.string.notifications_replied_to_me);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "notifications_type";
        private static View rootView;
        private static SwipeRefreshLayout mSwipeRefreshLayout;
        private static RecyclerView mRecyclerView;
        private static PostAdapter mAdapter;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final int type = getArguments().getInt(ARG_SECTION_NUMBER);
            rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
            mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_notifications_swipe_refresh_layout);
            mSwipeRefreshLayout.setColorSchemeResources(
                    R.color.material_light_blue_700,
                    R.color.material_red_700,
                    R.color.material_orange_700,
                    R.color.material_light_green_700
            );
            //RecyclerView
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.notifications_list);
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    //TODO 加载更多提醒
                }
            });
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new PostAdapter(getActivity(), true);
            mAdapter.setData(new ArrayList<Map<String, Object>>());
            mRecyclerView.setAdapter(mAdapter);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new GetNotificationsTask(type, false, mSwipeRefreshLayout, mRecyclerView, mAdapter, 1).execute();
                }
            });
            new GetNotificationsTask(type, true, mSwipeRefreshLayout, mRecyclerView, mAdapter, 1).execute();
            return rootView;
        }

        public class GetNotificationsTask extends AsyncTask<Void, Void, JSONObject> {
            private int targetPage;
            private int type;
            private String keyName;
            private Boolean loadFromCache;
            private SwipeRefreshLayout mSwipeRefreshLayout;
            private RecyclerView mRecyclerView;
            private PostAdapter mAdapter;
            public GetNotificationsTask(int type,
                                        Boolean loadFromCache,
                                        SwipeRefreshLayout mSwipeRefreshLayout,
                                        RecyclerView mRecyclerView,
                                        PostAdapter mAdapter,
                                        int targetPage) {
                this.targetPage = targetPage;
                this.type = type;
                this.keyName = type == 1 ? "ReplyArray" : "MentionArray";
                this.loadFromCache = loadFromCache;
                this.mSwipeRefreshLayout = mSwipeRefreshLayout;
                this.mRecyclerView = mRecyclerView;
                this.mAdapter = mAdapter;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if(!loadFromCache) {
                    mSwipeRefreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(true);
                        }
                    });
                }
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                super.onPostExecute(jsonObject);
                int status = 0;
                if(loadFromCache){
                    status = 1;
                }
                //先保存缓存
                if(jsonObject != null && !loadFromCache){
                    try {
                        status = jsonObject.getInt("Status");
                        SharedPreferences.Editor cacheEditor = CarbonForumApplication.cacheSharedPreferences.edit();
                        //cacheEditor.putString("notifications" + keyName + "Cache", jsonObject.toString(0));
                        cacheEditor.putString("notificationsMentionArrayCache", jsonObject.toString(0));
                        cacheEditor.putString("notificationsReplyArrayCache", jsonObject.toString(0));
                        cacheEditor.apply();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                //更新界面
                List<Map<String, Object>> list;
                list = JSONUtil.jsonObject2List(jsonObject, keyName);
                //防止异步任务未完成时，用户按下返回，Fragment被GC，造成NullPointer
                if(mRecyclerView != null && mSwipeRefreshLayout !=null && mAdapter != null && rootView != null && getActivity() != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Log.d("Status : ", keyName + String.valueOf(status));
                    if (status == 1) {
                        if(list != null && !list.isEmpty()){
                            Log.d("Action : ", keyName + " SetData");
                            mAdapter.setData(list);
                            mAdapter.notifyDataSetChanged();
                        }else{
                            //新注册用户，网络正常但是当前无任何通知，做个提示
                            Snackbar.make(rootView, R.string.empty_notification, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }
                    } else {
                        Snackbar.make(rootView, R.string.network_error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }
                //在提及我的的Tab中，从缓存中加载一次后，再从网络上更新一次
                if(type == 1 && loadFromCache){
                    new GetNotificationsTask(type, false, mSwipeRefreshLayout, mRecyclerView, mAdapter, 1).execute();
                }
            }

            @Override
            protected JSONObject doInBackground(Void... params) {
                if(loadFromCache){
                    return JSONUtil.jsonString2Object(
                            CarbonForumApplication.cacheSharedPreferences.
                                    getString("notifications" + keyName + "Cache", "{\"Status\":1, \"" + keyName + "\":[]}")
                    );
                }else {
                    return HttpUtil.postRequest(getActivity(), APIAddress.NOTIFICATIONS_URL, new HashMap<String, String>(), false, true);
                }
            }

        }
    }

}