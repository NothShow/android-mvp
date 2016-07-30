package com.android.mvp.thread;


import android.os.Handler;
import android.os.Looper;

import com.android.mvp.base.BaseThread;


public class HandlerThread implements BaseThread {

    private Handler mHandler;

    public HandlerThread() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }
}
