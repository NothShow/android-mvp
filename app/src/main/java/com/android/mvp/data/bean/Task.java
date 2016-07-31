package com.android.mvp.data.bean;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.android.mvp.BR;

public final class Task extends BaseObservable {

    private long mId;
    private String mTitle;

    public Task() {
        this.mId = 0L;
        this.mTitle = "";
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    @Bindable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
        notifyPropertyChanged(BR.title);
    }
}
