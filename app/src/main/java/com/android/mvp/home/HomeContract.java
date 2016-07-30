package com.android.mvp.home;


import com.android.mvp.base.BasePresenter;
import com.android.mvp.base.BaseView;
import com.android.mvp.data.bean.Task;

import java.util.List;

public class HomeContract {

    public interface View extends BaseView<Presenter> {

        void onTasksLoaded(List<Task> tasks);

        void onTasksNotAvailable();

        void onError(int code, String message);

    }

    public interface Presenter extends BasePresenter {

        void loadTasks(boolean refresh);

    }
}
