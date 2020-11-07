package com.admob.admobevwindow;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 玉米
 * @date 2020-11-07
 * @describe 埋点操作类 分为两种：1.当前所有已触发的埋点 2.当前页面的所有埋点（注意这是需要配合@AgentEv注解一起使用）
 */
public class ADMobEv implements Application.ActivityLifecycleCallbacks {
    private static ADMobEv INSTANCE = null;
    private Application application;
    //当前activity
    private Movent currentMovent;
    private static List<Movent.EventBean> eventBeanList = new ArrayList<>();
    //注解内容列表
    private List<Movent> moventList = new ArrayList<>();

    public static ADMobEv getInstance() {
        if (INSTANCE == null) {
            synchronized (ADMobEv.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ADMobEv();
                }
            }
        }
        return INSTANCE;
    }

    public void addEv(String id, String name) {
        eventBeanList.add(0, new Movent.EventBean(id, name));
    }

    public List<Movent.EventBean> getUpList() {
        return eventBeanList;
    }

    public void init(Application application, Class... clazz) {
        this.application = application;
        if (clazz == null) {
            this.application.registerActivityLifecycleCallbacks(this);
            return;
        }
        try {
            for (int l = 0; l < clazz.length; l++) {
                Field field[] = clazz[l].getFields();
                Object obj = clazz[l].newInstance();
                for (int i = 0; i < field.length; i++) {
                    Field fd = field[i];
                    AgentEv t = fd.getAnnotation(AgentEv.class);
                    if (t == null) continue;
                    String annoValue = t.act();
                    String descValue = t.desc();
                    String fieldValue = (String) fd.get(obj);
                    int index = isHas(annoValue);
                    if (index == -1) {
                        Movent mov = new Movent(annoValue);
                        mov.addEvent(fieldValue, descValue);
                        moventList.add(mov);
                    } else {
                        moventList.get(index).addEvent(fieldValue, descValue);
                    }
                }
            }

            this.application.registerActivityLifecycleCallbacks(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回相应下标
     *
     * @param name
     * @return
     */
    private int isHas(String name) {
        int i = 0;
        for (Movent m : moventList) {
            if (name.equals(m.getActivity_name())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * 切换到当前activity类下的埋点
     */
    private void switchActList(String act) {
        for (Movent m : moventList) {
            if (m.getActivity_name().equals(act)) {
                currentMovent = m;
                return;
            }
        }
    }

    /**
     * 获取当前activity下所有事件
     *
     * @return
     */
    public Movent getCurrentMovent() {

        return currentMovent;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (activity != null) {
            currentMovent = null;
            if (activity.getLocalClassName().indexOf(".") != -1) {
                String[] strs = activity.getLocalClassName().split("\\.");
                String act = strs[strs.length - 1];
                switchActList(act);
            } else {
                switchActList(activity.getLocalClassName());
            }


        }

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {


    }

    @Override
    public void onActivityPostCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        //setContentview之后
        if (activity != null) {
            FloatView floatView = new FloatView(activity);
            activity.addContentView(floatView, new ViewGroup.LayoutParams(120, 120));
        }

    }
}

