package com.admob.admobevwindow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author 玉米
 * @date 2020-11-07
 * @describe 埋点弹窗分为两种：1.当前所有已触发的埋点 2.当前页面的所有埋点（注意这是需要配合注解的）
 */
public class MoventPopupWindow extends PopupWindow {
    private Context context;

    //附加在view上层
    private View attachView;
    private TextView currentUp, currentPage;
    private MoventListView moventListView;
    private int selector = 0; //0 -> 当前触发 默认， 1->当前页面

    public MoventPopupWindow(Context context, View attachView) {
        super(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.context = context;
        this.attachView = attachView;
        init();
        initListener();
    }

    @Override
    public boolean isShowing() {
        try {
            if (moventListView != null) {
                if (selector == 0) {

                    moventListView.updateView(ADMobEv.getInstance().getUpList());
                } else {
                    if (ADMobEv.getInstance().getCurrentMovent() != null) {
                        moventListView.updateView(ADMobEv.getInstance().getCurrentMovent().getEvent());
                    } else {
                        moventListView.updateView(new ArrayList<Movent.EventBean>());
                    }

                }
            }
        }catch (Exception e) {

        }


        return super.isShowing();
    }

    private void init() {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.admobevwindow_movent_popup_widget, null, false);
            currentPage = view.findViewById(R.id.admobevwindow_current_page);
            currentUp = view.findViewById(R.id.admobevwindow_current_update);
            if (selector == 0) {
                currentUp.setTextColor(Color.parseColor("#03a9f4"));
            }
            moventListView = view.findViewById(R.id.movent_list);
            setContentView(view);
            setFocusable(true);
            setTouchable(true);
            setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            setClippingEnabled(false);
        }
        catch (Exception e) {

        }
    }

    private void initListener() {
        if(currentUp == null && currentPage == null) {
            return;
        }
        currentUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selector != 0) {
                    currentUp.setTextColor(Color.parseColor("#03a9f4"));
                    currentPage.setTextColor(Color.parseColor("#333333"));
                    selector = 0;
                    moventListView.updateView(ADMobEv.getInstance().getUpList());
                }

            }
        });
        currentPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selector != 1) {
                    currentPage.setTextColor(Color.parseColor("#03a9f4"));
                    currentUp.setTextColor(Color.parseColor("#333333"));
                    selector = 1;
                    if (ADMobEv.getInstance().getCurrentMovent() != null) {
                        moventListView.updateView(ADMobEv.getInstance().getCurrentMovent().getEvent());
                    } else {
                        moventListView.updateView(new ArrayList<Movent.EventBean>());
                    }
                }

            }
        });
    }


    public void show() {
        if(attachView == null) {
            return;
        }
        showAtLocation(attachView, Gravity.START, 0, 0);
    }


}
