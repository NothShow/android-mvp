package com.android.mvp.data;


import com.android.mvp.data.bean.Task;

import java.util.List;

public interface Repository {

    List<Task> getTasks();

    List<Task> refreshTasks();
}
