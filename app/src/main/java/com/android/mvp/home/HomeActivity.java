package com.android.mvp.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;

import com.android.mvp.R;
import com.android.mvp.base.BaseScheduler;
import com.android.mvp.base.BaseThread;
import com.android.mvp.data.bean.Task;
import com.android.mvp.data.impl.TaskRepository;
import com.android.mvp.injection.Injection;
import com.android.mvp.scheduler.ThreadPoolScheduler;
import com.android.mvp.thread.HandlerThread;

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    private ListView mList;
    private HomeListAdapter mListAdapter;

    private TaskRepository mRepository;
    private BaseScheduler mScheduler;
    private BaseThread mMainThread;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mList = (ListView)findViewById(R.id.list);

        mListAdapter = new HomeListAdapter(this);
        mList.setAdapter(mListAdapter);

        mRepository = TaskRepository.getInstance(
                Injection.provideLocalProxy(),
                Injection.provideRemoteProxy());
        mScheduler = ThreadPoolScheduler.getInstance();
        mMainThread = new HandlerThread();
    }

    @Override
    public void onResume() {
        super.onResume();
        initPage();
    }

    private void initPage() {
        mScheduler.execute(new Runnable() {
            @Override
            public void run() {
                List<Task> tasks = mRepository.getTasks();
                onTasksLoaded(tasks);

                tasks = mRepository.refreshTasks();
                onTasksLoaded(tasks);
            }
        });
    }

    private void onTasksLoaded(final List<Task> tasks) {
        if (tasks != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mListAdapter.setTasks(tasks);
                }
            });
        }
    }
}
