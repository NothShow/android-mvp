package com.android.mvp.scheduler;

import com.android.mvp.base.BaseScheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SingleThreadScheduler implements BaseScheduler {

    private static SingleThreadScheduler sInstance;

    public static SingleThreadScheduler getInstance() {
        if(sInstance == null) {
            synchronized (SingleThreadScheduler.class) {
                if(sInstance == null) {
                    sInstance = new SingleThreadScheduler();
                }
            }
        }
        return sInstance;
    }

    private ExecutorService mExecutorService;

    private SingleThreadScheduler() {
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
