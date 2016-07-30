package com.android.mvp.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.mvp.R;
import com.android.mvp.data.bean.Task;


import java.util.LinkedList;
import java.util.List;


public class HomeListAdapter extends BaseAdapter {

    private List<Task> mTasks = new LinkedList<>();

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public HomeListAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setTasks(List<Task> tasks) {
        if (tasks != null) {
            mTasks = tasks;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mTasks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        Task task;
        task = mTasks.get(position);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_home_item, null);
            viewHolder = new ViewHolder();

            viewHolder.tvItemTitle = (TextView)convertView.findViewById(R.id.tv_item_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        bindData(viewHolder, task);
        return convertView;
    }

    private void bindData(ViewHolder viewHolder, Task task) {
        viewHolder.tvItemTitle.setText(task.getTitle());
    }


    static class ViewHolder {
        TextView tvItemTitle;
    }
}
