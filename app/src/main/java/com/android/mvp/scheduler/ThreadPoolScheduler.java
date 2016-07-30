package com.android.mvp.scheduler;

import com.android.mvp.base.BaseScheduler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolScheduler implements BaseScheduler {

    private static ThreadPoolScheduler sInstance;
    public static ThreadPoolScheduler getInstance() {
        if (sInstance == null) {
            synchronized (ThreadPoolScheduler.class) {
                if (sInstance == null) {
                    sInstance = new ThreadPoolScheduler();
                }
            }
        }
        return sInstance;
    }

    private ExecutorService mExecutorService;

    public ThreadPoolScheduler() {
        mExecutorService = Executors.newCachedThreadPool();
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
