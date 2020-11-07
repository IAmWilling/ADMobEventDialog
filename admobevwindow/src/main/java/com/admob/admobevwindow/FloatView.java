package com.admob.admobevwindow;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * @author 玉米
 * @date 2020-11-07
 * @describe 可拖动的悬浮埋点按钮View
 */
public class FloatView extends View implements ObjectAnimator.AnimatorListener {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mWidth;
    private int mHeight;
    private float curX;
    private float curY;
    private float translateX;
    private float translateY;
    //最小滑动距离
    private float minHd;
    private Context context;
    private float mVpHeight, mVpWidth;
    private ObjectAnimator objectAnimator;
    private ViewGroup parent;
    //折叠状态：true 折叠  -  false 非折叠
    private boolean isFold = true;
    private MoventPopupWindow popupWindow;

    public FloatView(Context context) {
        super(context);
        init(context);
    }

    public FloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        mPaint.setColor(Color.parseColor("#03a9f4"));
        mPaint.setStyle(Paint.Style.FILL);
        setClickable(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        minHd = viewConfiguration.getScaledTouchSlop();
        objectAnimator = new ObjectAnimator();
        objectAnimator.addListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                curX = event.getRawX();
                curY = event.getRawY();
                if (objectAnimator.isRunning()) {
                    objectAnimator.cancel();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float movX = event.getRawX() - curX;
                float movY = event.getRawY() - curY;
                float endY = translateY + movY;
                float endX = translateX + movX;
                //限制距离
                if (!((endX + mWidth) >= (mVpWidth) || (endX + mWidth) <= 0 || (endY + mHeight) >= mVpHeight || endY <= 0)) {
                    setTranslationX(translateX + movX);
                    setTranslationY(translateY + movY);
                }
                break;
            case MotionEvent.ACTION_UP:
                translateX = getTranslationX();
                translateY = getTranslationY();
                //回到边缘位置 不遮挡测试 调试
                if ((translateX >= mVpWidth / 2 - mWidth / 2)) {
                    starAnimate(mVpWidth - mWidth);
                } else {
                    starAnimate(0);
                }
                if (Math.abs(curX - event.getRawX()) <= minHd && Math.abs(curY - event.getRawY()) <= minHd) {
                    if (popupWindow == null) {
                        popupWindow = new MoventPopupWindow(getContext(), parent);
                    }
                    popupWindow.show();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        parent = (ViewGroup) getParent();
        if (parent != null) {
            mVpHeight = parent.getHeight() * 1.0f;
            mVpWidth = parent.getWidth() * 1.0f;

            setTranslationY(mVpHeight - mHeight);
            setTranslationX(mVpWidth - mWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mRadius = Math.min(mWidth, mHeight) / 2;
        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaint);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.WHITE);
        p.setTextSize(55);
        float textWidth = p.measureText("稳");
        float Textx = mWidth / 2 - textWidth / 2;
        Paint.FontMetrics fontMetrics = p.getFontMetrics();
        float dy = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
        float Texty = mHeight / 2 + dy;
        canvas.drawText("稳", Textx, Texty, p);
    }

    private void starAnimate(float cx) {
        objectAnimator.setDuration(300);
        objectAnimator.setTarget(this);
        objectAnimator.setPropertyName("translationX");
        objectAnimator.setFloatValues(translateX, cx);
        objectAnimator.start();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        //保存更新过的x距离
        translateX = getTranslationX();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
