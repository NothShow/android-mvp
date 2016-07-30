package com.android.mvp.data.bean;


public final class Task {

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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
