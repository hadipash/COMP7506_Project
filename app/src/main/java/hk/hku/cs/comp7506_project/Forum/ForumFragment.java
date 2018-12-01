package hk.hku.cs.comp7506_project.Forum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import hk.hku.cs.comp7506_project.Forum.adapter.TopicAdapter;
import hk.hku.cs.comp7506_project.Forum.application.CarbonForumApplication;
import hk.hku.cs.comp7506_project.Forum.config.APIAddress;;
import hk.hku.cs.comp7506_project.Forum.util.HttpUtil;
import hk.hku.cs.comp7506_project.Forum.util.JSONUtil;
import hk.hku.cs.comp7506_project.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//http://stackoverflow.com/questions/28150100/setsupportactionbar-throws-error/28150167
public class ForumFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView ;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton mFloatingActionButton;
    private TopicAdapter mAdapter;
    private static View rootView;
    //private SharedPreferences mSharedPreferences;
    //private ActionBarDrawerToggle mDrawerToggle;
    private int currentPage = 0;
    private int totalPage = 65536;
    private Boolean enableScrollListener = true;
    private List<Map<String,Object>> topicList = new ArrayList<>();

    private static final String ARG_SECTION_NUMBER = "notifications_type";

    public ForumFragment() {}

    public ForumFragment newInstance(int sectionNumber){
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_index, container, false);

        //mSharedPreferences = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        // 设置ToolBar


        mFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewActivity.class);
                startActivity(intent);

            }
        });
        if(!CarbonForumApplication.isLoggedIn()){ mFloatingActionButton.setVisibility(View.INVISIBLE); }
        else { mFloatingActionButton.setVisibility(View.VISIBLE); }

        //下拉刷新监听器
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_index_swipe_refresh_layout);
        //设置刷新时动画的颜色，可以设置4个
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.material_light_blue_700,
                R.color.material_red_700,
                R.color.material_orange_700,
                R.color.material_light_green_700
        );
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //RecyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.topic_list);
        //使RecyclerView保持固定的大小，这样会提高RecyclerView的性能
        mRecyclerView.setHasFixedSize(true);
        // 创建一个线性布局管理器
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //setOnScrollListener已废弃，使用addOnScrollListener需要在使用后用clearOnScrollListeners()移除监听器
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();
                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem >= (totalItemCount - 5) && enableScrollListener && currentPage < totalPage) {
                        //加载更多功能的代码
                        loadTopic(currentPage + 1, false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                /*
                if (dx > 0) {
                    //大于0表示，正在向右滚动
                } else {
                    //小于等于0 表示停止或向左滚动
                }
                */
            }
        });
        // 设置布局管理器
        mRecyclerView.setLayoutManager(layoutManager);
        //设置Item默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //指定数据集
        mAdapter = new TopicAdapter(getActivity());
        mAdapter.setData(topicList);
        //设置Adapter
        mRecyclerView.setAdapter(mAdapter);
        //Activity渲染完毕时加载帖子，使用缓存
        loadTopic(1, true);
        return rootView;
    }

    //加载帖子列表
    private void loadTopic(int targetPage, Boolean enableCache) {
        new GetTopicsTask(targetPage, enableCache).execute();
    }

    //下拉刷新事件
    @Override
    public void onRefresh() {
        //if(!mSwipeRefreshLayout.isRefreshing()){
        loadTopic(1, false);
        if(!CarbonForumApplication.isLoggedIn()){ mFloatingActionButton.setVisibility(View.INVISIBLE); }
        else { mFloatingActionButton.setVisibility(View.VISIBLE); }
        //}
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
    }

    public class GetTopicsTask extends AsyncTask<Void, Void, List<Map<String,Object>>> {
        private int targetPage;
        private Boolean enableCache;
        private int positionStart;
        public GetTopicsTask(int targetPage, Boolean enableCache) {
            this.targetPage = targetPage;
            this.enableCache = enableCache;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            enableScrollListener = false;
            if(enableCache){
                topicList = JSONUtil.jsonObject2List(JSONUtil.jsonString2Object(
                        CarbonForumApplication.cacheSharedPreferences.getString("topicsCache", "{\"Status\":1, \"TopicsArray\":[]}"))
                        , "TopicsArray");
                if(topicList != null){
                    mAdapter.setData(topicList);
                    mAdapter.notifyDataSetChanged();
                }
            }
            mSwipeRefreshLayout.post(new Runnable(){
                @Override
                public void run(){
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });

            //Toast.makeText(IndexActivity.this, "Before AsyncTask", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> result) {
            super.onPostExecute(result);
            if(result!=null && !result.isEmpty()) {
                if (targetPage > 1) {
                    positionStart = topicList.size() - 1;
                    topicList.addAll(result);
                    mAdapter.setData(topicList);
                    //局部刷新，更好的性能
                    mAdapter.notifyItemRangeChanged(positionStart, mAdapter.getItemCount());
                } else {
                    topicList = result;
                    mAdapter.setData(topicList);
                    //全部刷新
                    mAdapter.notifyDataSetChanged();
                }
                //更新当前页数
                currentPage = targetPage;
            }else{
                Snackbar.make(mFloatingActionButton, R.string.network_error, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            //移除刷新控件
            mSwipeRefreshLayout.setRefreshing(false);
            enableScrollListener = true;
            //Toast.makeText(IndexActivity.this, "AsyncTask End", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected List<Map<String, Object>> doInBackground(Void... params) {
            List<Map<String,Object>> list;
            JSONObject jsonObject = HttpUtil.postRequest(getContext(), APIAddress.HOME_URL(targetPage), null, false, false);
            //Log.v("JSON", str);
            if(jsonObject != null){
                try {
                    totalPage = jsonObject.getInt("TotalPage");
                }catch(JSONException e){
                    e.printStackTrace();
                }
                if(targetPage == 1){
                    try {
                        SharedPreferences.Editor cacheEditor = CarbonForumApplication.cacheSharedPreferences.edit();
                        cacheEditor.putString("topicsCache", jsonObject.toString(0));
                        cacheEditor.apply();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
            list = JSONUtil.jsonObject2List(jsonObject, "TopicsArray");
            //Log.v("List", list.toString());
            return list;
        }

    }
}