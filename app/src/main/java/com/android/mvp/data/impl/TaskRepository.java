package com.android.mvp.data.impl;


import android.support.annotation.NonNull;


import com.android.mvp.data.LocalProxy;
import com.android.mvp.data.RemoteProxy;
import com.android.mvp.data.Repository;
import com.android.mvp.data.bean.Task;

import java.util.List;

public class TaskRepository implements Repository {

    private static TaskRepository sInstance;

    public static TaskRepository getInstance() {
        if (sInstance == null) {
            synchronized (TaskRepository.class) {
                if (sInstance == null) {
                    sInstance = new TaskRepository();
                }
            }
        }
        return sInstance;
    }

    public static TaskRepository getInstance(LocalProxy localProxy, RemoteProxy remoteProxy) {
        TaskRepository taskRepository = getInstance();
        taskRepository.setLocalProxy(localProxy);
        taskRepository.setRemoteProxy(remoteProxy);
        return taskRepository;
    }

    public static void create(LocalProxy localProxy, RemoteProxy remoteProxy) {
        TaskRepository taskRepository = getInstance();
        taskRepository.setLocalProxy(localProxy);
        taskRepository.setRemoteProxy(remoteProxy);
    }

    private LocalProxy mLocalProxy;
    private RemoteProxy mRemoteProxy;

    public TaskRepository() {
    }

    public TaskRepository(@NonNull LocalProxy localProxy, @NonNull RemoteProxy remoteProxy) {
        mLocalProxy = localProxy;
        mRemoteProxy = remoteProxy;
    }

    public void setLocalProxy(LocalProxy localProxy) {
        mLocalProxy = localProxy;
    }

    public LocalProxy getLocalProxy() {
        return mLocalProxy;
    }

    public RemoteProxy getRemoteProxy() {
        return mRemoteProxy;
    }

    public void setRemoteProxy(RemoteProxy remoteProxy) {
        mRemoteProxy = remoteProxy;
    }


    @Override
    public List<Task> getTasks() {
        return mLocalProxy.getAll();
    }

    @Override
    public List<Task> refreshTasks() {
        return mRemoteProxy.getAllTask();
    }
}
