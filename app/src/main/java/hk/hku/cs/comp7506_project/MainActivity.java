package hk.hku.cs.comp7506_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.RecyclerViewCacheUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import hk.hku.cs.comp7506_project.Calculator.Calculator;
import hk.hku.cs.comp7506_project.Forum.ForumFragment;
import hk.hku.cs.comp7506_project.Forum.LoginActivity;
import hk.hku.cs.comp7506_project.Forum.NotificationsActivity;
import hk.hku.cs.comp7506_project.Forum.RegisterActivity;
import hk.hku.cs.comp7506_project.Forum.SettingsActivity;
import hk.hku.cs.comp7506_project.Forum.application.CarbonForumApplication;
import hk.hku.cs.comp7506_project.Forum.config.APIAddress;
import hk.hku.cs.comp7506_project.Forum.service.PushService;
import hk.hku.cs.comp7506_project.News.NewsFeedFragment;
import hk.hku.cs.comp7506_project.Wiki.StockLine;
import hk.hku.cs.comp7506_project.Wiki.WikiPage;



public class MainActivity extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Toolbar mToolbar;
    private Drawer mDrawer = null;
    private AccountHeader headerResult = null;

    private static final String TAG = "MainActivity";

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册一个广播用来登录和退出时刷新Drawer
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshDrawer");
        LocalBroadcastManager.getInstance(this).registerReceiver(mRefreshDrawerBroadcastReceiver, intentFilter);
        //mSharedPreferences = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        // 设置ToolBar
        mToolbar = findViewById(R.id.toolbar);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);//把Toolbar当做ActionBar给设置了
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(R.string.app_name);
            //mToolbar.bringToFront();
            //toolbar.setLogo(R.drawable.ic_launcher);
            // toolbar.setSubtitle("Sub title");

            refreshDrawer(savedInstanceState);
        }
        refreshDrawer(savedInstanceState);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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

    private BroadcastReceiver mRefreshDrawerBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshDrawer")) {
                refreshDrawer(null);
            }
        }
    };

    private void refreshDrawer(Bundle savedInstanceState) {
        try {
            //Log.v("UserID", mSharedPreferences.getString("UserID", "0"));
            if (!CarbonForumApplication.isLoggedIn()) { //未登录
                //隐藏发帖按钮
                final IProfile profile = new ProfileDrawerItem()
                        .withName("Not logged in")
                        .withIcon(R.drawable.profile)
                        .withIdentifier(0);
                // Create the AccountHeader
                headerResult = new AccountHeaderBuilder()
                        .withActivity(this)
                        .withHeaderBackground(R.drawable.header)
                        .withSelectionListEnabledForSingleProfile(false)
                        .addProfiles(
                                profile
                        )
                        .withSavedInstance(savedInstanceState)
                        .build();
            } else { //已登录
                //显示发帖按钮
                final IProfile profile = new ProfileDrawerItem()
                        .withName(CarbonForumApplication.userInfo.getString("UserName", "lincanbin"))
                        .withEmail(CarbonForumApplication.userInfo.getString("UserMail", CarbonForumApplication.userInfo.getString("UserName", "lincanbin")))
                        .withIcon(Uri.parse(APIAddress.MIDDLE_AVATAR_URL(CarbonForumApplication.userInfo.getString("UserID", "0"), "large")))
                        .withIdentifier(Integer.parseInt(CarbonForumApplication.userInfo.getString("UserID", "0")));
                // Create the AccountHeader
                headerResult = new AccountHeaderBuilder()
                        .withActivity(this)
                        .withHeaderBackground(R.drawable.header)
                        .withSelectionListEnabledForSingleProfile(false)
                        //.withTranslucentStatusBar(false)
                        .addProfiles(
                                profile,
                                //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                                new ProfileSettingDrawerItem()
                                        .withName(getString(R.string.change_account))
                                        .withIcon(GoogleMaterial.Icon.gmd_accounts)
                                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                            @Override
                                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                                MainActivity.this.startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                                return false;
                                            }
                                        }),
                                new ProfileSettingDrawerItem()
                                        .withName(getString(R.string.log_out))
                                        .withIcon(GoogleMaterial.Icon.gmd_close)
                                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                            @Override
                                            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                                                CarbonForumApplication.userInfo.edit().clear().apply();
                                                refreshDrawer(null);
                                                return false;
                                            }
                                        })
                        )
                        .withSavedInstance(savedInstanceState)
                        .build();
                //开启推送
                startService(new Intent(MainActivity.this, PushService.class));
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        //Create the drawer
        DrawerBuilder mDrawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withActionBarDrawerToggle(true)
                .withToolbar(mToolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                // .withTranslucentStatusBar(false)
                .addDrawerItems(
                        new PrimaryDrawerItem().
                                withName(R.string.app_name).
                                withIcon(GoogleMaterial.Icon.gmd_home). // home
                                withSetSelected(true).
                                withIdentifier(1).
                                withSelectable(true),
                        new PrimaryDrawerItem().
                                withName(R.string.refresh).
                                withIcon(GoogleMaterial.Icon.gmd_refresh).
                                withIdentifier(2).
                                withSelectable(false),
                        new PrimaryDrawerItem().
                                withName(R.string.calculator).
                                withIdentifier(3).
                                withSelectable(false),
                        new PrimaryDrawerItem().
                                withName(R.string.Stocks_line).
                                withIdentifier(4).
                                withSelectable(false),
                        new PrimaryDrawerItem().
                                withName(R.string.Fin_book).
                                withIdentifier(5).
                                withSelectable(false),
                        new DividerDrawerItem()
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem
                        if (drawerItem != null) {
                            Intent intent = null;
//                            if (drawerItem.getIdentifier() == 6) {
//                                intent = new Intent(MainActivity.this, LoginActivity.class);
//                            } else if (drawerItem.getIdentifier() == 7) {
//                                intent = new Intent(MainActivity.this, RegisterActivity.class);
//                            } else if (drawerItem.getIdentifier() == 8) {
//                                intent = new Intent(MainActivity.this, NotificationsActivity.class);
//                            } else if (drawerItem.getIdentifier() == 9) {
//                                intent = new Intent(MainActivity.this, SettingsActivity.class);
//                            }
                            switch (drawerItem.getIdentifier()) {
                                case 3:
                                    Log.d(TAG, "Calculator menu clicked");
                                    intent = new Intent(MainActivity.this, Calculator.class);
                                    break;
                                case 4:
                                    Log.d(TAG, "WikiBook menu clicked.");
                                    intent = new Intent(MainActivity.this, StockLine.class);
                                    break;
                                case 5:
                                    Log.d(TAG, "TestWiki menu clicked.");

                                    // TODO: wiki
                                    WikiPage wiki = new WikiPage();
                                    String word = "apple";
                                    wiki.popItUp(word, MainActivity.this,
                                            getWindow().getDecorView().getRootView());
                                    break;
                                case 6:
                                    intent = new Intent(MainActivity.this, LoginActivity.class);
                                    break;
                                case 7:
                                    intent = new Intent(MainActivity.this, RegisterActivity.class);
                                    break;
                                case 8:
                                    intent = new Intent(MainActivity.this, NotificationsActivity.class);
                                    break;
                                case 9:
                                    intent = new Intent(MainActivity.this, SettingsActivity.class);
                                    break;

                                default:
                                    Log.d(TAG, "unknown drawerItem.getIdentifier.");
//
                            }
                            if (intent != null) {
                                startActivity(intent);
                            }
                        }

                        return false;
                    }
                });


        if (!CarbonForumApplication.isLoggedIn()) { //未登录
            mDrawerBuilder.addDrawerItems(
                    new PrimaryDrawerItem().
                            withName(R.string.title_activity_login).
                            withIcon(GoogleMaterial.Icon.gmd_account).
                            withIdentifier(6).
                            withSelectable(false),
                    new PrimaryDrawerItem().
                            withName(R.string.title_activity_register).
                            withIcon(GoogleMaterial.Icon.gmd_account_add).
                            withIdentifier(7).
                            withSelectable(false)
            );
        } else { //已登录
            mDrawerBuilder.addDrawerItems(
                    new PrimaryDrawerItem()
                            .withName(R.string.title_activity_notifications)
                            .withIcon(GoogleMaterial.Icon.gmd_notifications)
                            .withIdentifier(8)
                            .withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))
                            .withSelectable(false),
                    new PrimaryDrawerItem()
                            .withName(R.string.title_activity_settings)
                            .withIcon(GoogleMaterial.Icon.gmd_settings)
                            .withIdentifier(9)
                            .withSelectable(false)
            );
        }
        mDrawer = mDrawerBuilder.build();
        //if you have many different types of DrawerItems you can magically pre-cache those items to get a better scroll performance
        //make sure to init the cache after the DrawerBuilder was created as this will first clear the cache to make sure no old elements are in
        RecyclerViewCacheUtil.getInstance().withCacheSize(2).init(mDrawer);

        //only set the active selection or active profile if we do not recreate the activity
        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 1
            mDrawer.setSelection(1, false);

            //set the active profile
            //headerResult.setActiveProfile(profile);
        }
        //TODO:根据消息数量刷新Notification
        int notificationsNumber = Integer.parseInt(CarbonForumApplication.cacheSharedPreferences.getString("notificationsNumber", "0"));
        if (notificationsNumber > 0) {
            //添加消息通知
            mDrawer.updateBadge(6, new StringHolder(notificationsNumber + ""));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRefreshDrawerBroadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (mDrawer != null && mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private static final int NEWS_SECTION = 0;

        private static final int FORUM_SECTION = 1;


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

//        @Override
//        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a PlaceholderFragment (defined as a static inner class below).
//            return PlaceholderFragment.newInstance(position + 1);
//        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case NEWS_SECTION:
                    return new NewsFeedFragment();
                case FORUM_SECTION:
                    return new ForumFragment();

            }

            // return is required

            return new NewsFeedFragment();

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
                    return getString(R.string.news);
                case 1:
                    return getString(R.string.forum);
            }
            return null;
        }
    }
}
