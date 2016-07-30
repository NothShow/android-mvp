package com.android.mvp.data;


import com.android.mvp.data.bean.Task;

import java.util.List;

public interface LocalProxy {

    boolean insert(Task task);

    boolean delete(long taskId);

    Task get(long taskId);

    boolean update(Task task);

    List<Task> getAll();
}
