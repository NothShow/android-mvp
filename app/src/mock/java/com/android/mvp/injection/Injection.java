package com.android.mvp.injection;


import com.android.mvp.data.LocalProxy;
import com.android.mvp.data.RemoteProxy;
import com.android.mvp.data.bean.Task;
import com.android.mvp.data.impl.TaskDbApi;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Injection {
    public static RemoteProxy provideRemoteProxy() {
        return new RemoteProxy() {

            private List<Task> mTasks = makeFakeTasks();
            @Override
            public boolean addTask(Task task) {
                mTasks.add(task);
                return true;
            }

            @Override
            public boolean deleteTask(long taskId) {
                for (Task task : mTasks) {
                    if (task.getId() == taskId) {
                        mTasks.remove(task);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Task getTask(long taskId) {
                for (Task task : mTasks) {
                    if (task.getId() == taskId) {
                        return task;
                    }
                }
                return null;
            }

            @Override
            public boolean updateTask(Task task) {
                for (int i = 0; i < mTasks.size(); ++i) {
                    Task t = mTasks.get(i);
                    if (t.getId() == task.getId()) {
                        mTasks.set(i, task);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public List<Task> getAllTask() {
                return mTasks;
            }

            private List<Task> makeFakeTasks() {
                List<Task> tasks = new LinkedList<>();
                Date date = new Date();
                long time = date.getTime();
                for (int i = 0; i < 10; ++i) {
                    Task task = new Task();
                    task.setId(time + i);
                    task.setTitle("this is a test title " + String.valueOf(i));
                    tasks.add(task);
                }

                return tasks;
            }
        };
    }

    public static LocalProxy provideLocalProxy() {
        return new TaskDbApi();
    }
}
