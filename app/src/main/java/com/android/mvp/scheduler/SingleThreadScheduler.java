package com.android.mvp.scheduler;

import com.android.mvp.base.BaseScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingleThreadScheduler implements BaseScheduler {

    private ExecutorService mExecutorService;

    public SingleThreadScheduler() {
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(Runnable runnable) {
        mExecutorService.execute(runnable);
    }

    @Override
    public void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        mExecutorService.awaitTermination(timeout, unit);
    }
}
