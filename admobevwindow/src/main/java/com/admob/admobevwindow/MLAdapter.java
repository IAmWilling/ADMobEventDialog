package com.admob.admobevwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 玉米
 * @date 2020-11-07
 * @describe 用于ViewGroup适配器
 */
public class MLAdapter extends BaseAdapter {
    private List<Movent.EventBean> list = new ArrayList<>();
    private Context context;

    public MLAdapter(Context context, List<Movent.EventBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try{
            Movent.EventBean mb = list.get(position);
            View v = LayoutInflater.from(context).inflate(R.layout.admobevwindow_item, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.idTv.setText("id: " + mb.getId());
            holder.nameTv.setText("描述: " + mb.getName());
            return v;
        }catch (Exception e) {

        }
        return null;
    }

    private class ViewHolder {
        public TextView idTv, nameTv;

        public ViewHolder(View view) {
            idTv = view.findViewById(R.id.movent_id);
            nameTv = view.findViewById(R.id.movent_name);
        }
    }

    /**
     * 设置新数据
     *
     * @param list
     */
    public void setNewData(List<Movent.EventBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
