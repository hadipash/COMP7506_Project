package hk.hku.cs.comp7506_project.Wiki;

import android.os.Bundle;


import com.google.android.material.tabs.TabLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import hk.hku.cs.comp7506_project.R;
import hk.hku.cs.comp7506_project.Wiki.kline.KLineChartFragment;
import hk.hku.cs.comp7506_project.Wiki.kline.SimpleFragmentPagerAdapter;


public class StockLine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_line);


        TabLayout tabLayout = findViewById(R.id.tab);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        Fragment[] fragments = {KLineChartFragment.newInstance(1), KLineChartFragment.newInstance(7),
                KLineChartFragment.newInstance(30)};

        String[] titles = {"Daily", "Weekly", "Monthly"};
        viewPager.setOffscreenPageLimit(fragments.length);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FullScreenChartActivity.launch(StockLine.this, viewPager.getCurrentItem());
//            }
//        });
    }

}
