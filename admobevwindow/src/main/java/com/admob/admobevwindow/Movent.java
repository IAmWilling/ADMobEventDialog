package com.admob.admobevwindow;

import java.util.ArrayList;
import java.util.List;
/**
 * @author 玉米
 * @date 2020-11-07
 * @describe 埋点数据结构
 */
public class Movent {

    /**
     * activity_name : MainActivity
     * event : [{"id":"home_button_click_count","name":"首页按钮点击事件"}]
     */

    private String activity_name;
    private List<EventBean> event;

    public Movent(String activity_name) {
        this.activity_name = activity_name;
        this.event = new ArrayList<>();
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public List<EventBean> getEvent() {
        return event;
    }

    public void setEvent(List<EventBean> event) {
        this.event = event;
    }

    public void addEvent(String id, String desc) {
        event.add(new Movent.EventBean(id, desc));
    }

    public static class EventBean {
        /**
         * id : home_button_click_count
         * name : 首页按钮点击事件
         */

        private String id;
        private String name = "";

        public EventBean(String id, String desc) {
            this.id = id;
            this.name = desc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
