package com.android.mvp.data.impl;


import com.android.mvp.data.LocalProxy;
import com.android.mvp.data.bean.Task;

import java.util.List;

public class TaskDbApi implements LocalProxy {


    @Override
    public boolean insert(Task task) {
        return false;
    }

    @Override
    public boolean delete(long taskId) {
        return false;
    }

    @Override
    public Task get(long taskId) {
        return null;
    }

    @Override
    public boolean update(Task task) {
        return false;
    }

    @Override
    public List<Task> getAll() {
        return null;
    }
}
