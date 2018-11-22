package  hk.hku.cs.comp7506_project.Wiki.kline.view;

import android.content.Context;

import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;


/**
 * Created by guoziwei on 2017/11/15.
 */
public class NoTouchScrollViewpager extends ViewPager {

    public NoTouchScrollViewpager(Context context) {
        this(context, null);
    }

    public NoTouchScrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

}
