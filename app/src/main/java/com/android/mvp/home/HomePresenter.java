package com.android.mvp.home;


import com.android.mvp.base.BaseScheduler;
import com.android.mvp.base.BaseThread;
import com.android.mvp.data.Repository;
import com.android.mvp.data.bean.Task;
import com.android.mvp.home.tasks.LoadTask;

import java.util.List;


public class HomePresenter implements HomeContract.Presenter {

    private HomeContract.View mView;
    private Repository mRepository;
    private BaseScheduler mScheduler;
    private BaseThread mMainThread;
    private LoadTask mLoadTask;

    public HomePresenter(HomeContract.View view, BaseScheduler scheduler, BaseThread thread, Repository repository) {
        this.mView = view;
        this.mScheduler = scheduler;
        this.mMainThread = thread;
        this.mRepository = repository;

        mLoadTask = new LoadTask(mScheduler);
    }

    @Override
    public void loadTasks(boolean refresh) {
        LoadTask.RequestValues requestValues = new LoadTask.RequestValues();
        requestValues.setRefresh(refresh);
        requestValues.setRepository(mRepository);
        mLoadTask.setRequestValues(requestValues);

        mLoadTask.execute(new LoadTask.Callback<LoadTask.ResponseValues>() {

            @Override
            public void onSuccess(final LoadTask.ResponseValues response) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        List<Task> tasks = response.getTasks();
                        if (tasks != null && tasks.size() > 0) {
                            mView.onTasksLoaded(tasks);
                        } else {
                            mView.onTasksNotAvailable();
                        }
                    }
                });
            }

            @Override
            public void onError(final int code, final String msg) {
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.onError(code, msg);
                    }
                });
            }
        }, true);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
