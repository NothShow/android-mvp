package com.android.mvp.home;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.mvp.data.bean.Task;
import com.android.mvp.databinding.ActivityHomeItemBinding;


import java.util.LinkedList;
import java.util.List;


public class HomeListAdapter extends BaseAdapter {

    private List<Task> mTasks = new LinkedList<>();

    private Context mContext;
    private HomeContract.Presenter mPresenter;
    private LayoutInflater mLayoutInflater;

    public HomeListAdapter(Context context, HomeContract.Presenter presenter) {
        this.mContext = context;
        this.mPresenter = presenter;
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
        ActivityHomeItemBinding binding;
        Task task = mTasks.get(position);
        if (convertView == null) {
            binding = ActivityHomeItemBinding.inflate(mLayoutInflater, parent, false);
        } else {
            binding = DataBindingUtil.getBinding(convertView);
        }
        binding.setTask(task);
        binding.executePendingBindings();

        return binding.getRoot();
    }
}
