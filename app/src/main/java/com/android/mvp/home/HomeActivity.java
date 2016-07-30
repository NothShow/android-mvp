package com.android.mvp.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.android.mvp.R;
import com.android.mvp.base.BaseScheduler;
import com.android.mvp.base.BaseThread;
import com.android.mvp.data.bean.Task;
import com.android.mvp.data.impl.TaskRepository;
import com.android.mvp.injection.Injection;
import com.android.mvp.scheduler.ThreadPoolScheduler;
import com.android.mvp.thread.HandlerThread;

import java.util.List;


public class HomeActivity extends AppCompatActivity implements HomeContract.View {


    private ListView mListTodo;

    private HomeContract.Presenter mPresenter;

    private HomeListAdapter mListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mListTodo = (ListView)findViewById(R.id.list);

        Context context = getApplicationContext();
        BaseScheduler scheduler = ThreadPoolScheduler.getInstance();
        BaseThread thread = new HandlerThread();

        TaskRepository jobRepository = TaskRepository.getInstance(
                Injection.provideLocalProxy(),
                Injection.provideRemoteProxy());

        mPresenter = new HomePresenter(this, scheduler, thread, jobRepository);
        mPresenter.start();

        mListAdapter = new HomeListAdapter(context, mPresenter);
        mListTodo.setAdapter(mListAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadTasks(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.stop();
    }

    @Override
    public void onTasksLoaded(List<Task> tasks) {
        mListAdapter.setTasks(tasks);
    }

    @Override
    public void onTasksNotAvailable() {

    }

    @Override
    public void onError(int code, String message) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }
}
