package com.android.mvp.injection;


import com.android.mvp.data.LocalProxy;
import com.android.mvp.data.RemoteProxy;
import com.android.mvp.data.impl.TaskDbApi;
import com.android.mvp.data.impl.TaskServerApi;

public class Injection {
    public static RemoteProxy provideRemoteProxy() {
        return new TaskServerApi();
    }

    public static LocalProxy provideLocalProxy() {
        return new TaskDbApi();
    }
}
