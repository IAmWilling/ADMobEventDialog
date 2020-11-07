package com.admob.admobevwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 玉米
 * @time 2020-10-07
 * @describe 可滑动的埋点展示列表
 */
public class MoventListView extends ViewGroup {
    private MLAdapter adapter;
    //滑动辅助类
    private Scroller mScroller;
    //配合滑动速率计算类
    private VelocityTracker mVelocityTracker;
    //view配置信息
    private ViewConfiguration mViewConfiguration;
    //最小滑动像素
    private int mMinTouchSlop;
    //最小滑动速度
    private int mMinFlingVelocity;
    //最大滑动速度
    private int mMaxFlingVelocity;
    //上一次Y轴滑动距离
    private float mLastY;
    //最后一次Y轴滑动距离
    private float mEndY;
    //内容总高度
    private int mViewGroupContentHeight;
    //viewgroup总高度
    private int mViewGroupHeight;

    public MoventListView(Context context) {
        super(context);
        init(context);
    }

    public MoventListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MoventListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        adapter = new MLAdapter(context, new ArrayList<Movent.EventBean>());
        mScroller = new Scroller(context);
        mViewConfiguration = ViewConfiguration.get(context);
        mMinTouchSlop = mViewConfiguration.getScaledTouchSlop();
        mMinFlingVelocity = mViewConfiguration.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = mViewConfiguration.getScaledMaximumFlingVelocity();

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewGroupHeight = h;
    }

    /**
     * 添加布局
     */
    private void addMView() {
        removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            View v = adapter.getView(i, null, this);
            if (v != null) {
                addView(v);
            }
        }
    }

    /**
     * 更新布局
     */
    public void updateView(List<Movent.EventBean> list) {
        adapter.setNewData(list);
        addMView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        int left = 0;
        int right = 0;
        int bottom = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            top = i * view.getMeasuredHeight();
            right = view.getMeasuredWidth();
            bottom = top + view.getMeasuredHeight();
            mViewGroupContentHeight = bottom;
            view.layout(left, top, right, bottom);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                }
                if (!mScroller.isFinished()) {
                    //停止动画
                    mScroller.abortAnimation();
                }
                mLastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float movY = Math.abs(mLastY - ev.getY());
                if (movY > mMinTouchSlop) {
                    //大于滑动距离 果断消费 不再向子View传递 防止子View消费滑动距离 导致无法滑动viewgroup
                    return true;
                }

                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mViewGroupContentHeight <= mViewGroupHeight) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //为计算惯性速率做准备
                mVelocityTracker.addMovement(event);
                float movY = mLastY - event.getY();
                overScrollBy(0, (int) movY, 0, getScrollY(), 0, getRangY(), 0, 0, true);
                mLastY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                float mVelocityY = mVelocityTracker.getYVelocity();
                if (Math.abs(mVelocityY) > mMinFlingVelocity) {
                    mScroller.fling(0, getScrollY(), 0, (int) -mVelocityY, 0, 0, 0, getRangY());
                }
                mVelocityTracker.clear();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        if (mScroller.isFinished()) {
            super.scrollTo(scrollX, scrollY);
        }

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 获取可滑动的最大距离
     *
     * @return
     */
    private int getRangY() {
        return mViewGroupContentHeight - mViewGroupHeight;
    }
}
