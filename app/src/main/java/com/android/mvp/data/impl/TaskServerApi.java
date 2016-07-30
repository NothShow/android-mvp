package com.android.mvp.data.impl;


import com.android.mvp.data.RemoteProxy;
import com.android.mvp.data.bean.Task;

import java.util.List;

public class TaskServerApi implements RemoteProxy {

    @Override
    public boolean addTask(Task task) {
        return false;
    }

    @Override
    public boolean deleteTask(long taskId) {
        return false;
    }

    @Override
    public Task getTask(long taskId) {
        return null;
    }

    @Override
    public boolean updateTask(Task task) {
        return false;
    }

    @Override
    public List<Task> getAllTask() {
        return null;
    }
}
