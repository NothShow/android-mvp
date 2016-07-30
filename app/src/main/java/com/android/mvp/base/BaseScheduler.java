package com.android.mvp.base;

import java.util.concurrent.TimeUnit;

public interface BaseScheduler {

    void execute(Runnable runnable);

    void awaitTermination(long timeout, TimeUnit unit) throws InterruptedException;
}
