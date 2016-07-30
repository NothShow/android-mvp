package com.android.mvp.home.tasks;

import com.android.mvp.base.BaseScheduler;
import com.android.mvp.base.BaseTask;
import com.android.mvp.data.Repository;
import com.android.mvp.data.bean.Task;

import java.util.LinkedList;
import java.util.List;


public class LoadTask extends BaseTask<LoadTask.RequestValues, LoadTask.ResponseValues> {

    public static final class RequestValues implements BaseTask.RequestValues {
        private boolean mRefresh;
        private Repository mRepository;

        public boolean isRefresh() {
            return mRefresh;
        }

        public void setRefresh(boolean refresh) {
            mRefresh = refresh;
        }

        public Repository getRepository() {
            return mRepository;
        }

        public void setRepository(Repository repository) {
            mRepository = repository;
        }
    }

    public static final class ResponseValues implements BaseTask.ResponseValues {
        private List<Task> mTasks;

        public ResponseValues() {
            mTasks = new LinkedList<>();
        }

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }
    }

    public LoadTask(BaseScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void run() {

        getScheduler().execute(new Runnable() {
            @Override
            public void run() {
                LoadTask.RequestValues requestValues = getRequestValues();
                boolean refresh = requestValues.isRefresh();
                List<Task> tasks = requestValues.getRepository().getTasks();

                LoadTask.ResponseValues responseValues = new LoadTask.ResponseValues();
                responseValues.setTasks(tasks);
                setResponseValues(responseValues);

                notifySuccess();

                if (refresh) {
                    tasks = requestValues.getRepository().refreshTasks();
                    responseValues.setTasks(tasks);
                    notifySuccess();
                }
            }
        });

    }
}
