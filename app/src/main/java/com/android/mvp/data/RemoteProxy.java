package com.android.mvp.data;


import com.android.mvp.data.bean.Task;

import java.util.List;

public interface RemoteProxy {

    boolean addTask(Task task);

    boolean deleteTask(long taskId);

    Task getTask(long taskId);

    boolean updateTask(Task task);

    List<Task> getAllTask();
}
