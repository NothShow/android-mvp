package com.android.mvp.base;

public abstract class BaseTask<T extends BaseTask.RequestValues, R extends BaseTask.ResponseValues> implements Runnable {

    public interface RequestValues {
    }

    public interface ResponseValues {
    }

    public interface Callback<P> {
        void onSuccess(P response);
        void onError(int code, String msg);
    }

    private BaseScheduler mScheduler;
    private T mRequestValues;
    private R mResponseValues;
    private Callback<R> mCallback;

    public BaseTask(BaseScheduler scheduler) {
        mScheduler = scheduler;
    }

    public BaseScheduler getScheduler() {
        return mScheduler;
    }

    public void setScheduler(BaseScheduler mScheduler) {
        this.mScheduler = mScheduler;
    }

    public T getRequestValues() {
        return mRequestValues;
    }

    public void setRequestValues(T mRequestValues) {
        this.mRequestValues = mRequestValues;
    }

    public R getResponseValues() {
        return mResponseValues;
    }

    public void setResponseValues(R mResponseValues) {
        this.mResponseValues = mResponseValues;
    }

    public Callback<R> getCallback() {
        return mCallback;
    }

    public void setCallback(Callback<R> mCallback) {
        this.mCallback = mCallback;
    }

    public void execute(boolean schedule) {
        if (schedule) {
            mScheduler.execute(this);
        } else {
            run();
        }
    }

    public void execute(Callback<R> callback, boolean schedule) {
        setCallback(callback);
        if (schedule) {
            mScheduler.execute(this);
        } else {
            run();
        }
    }

    @Override
    public abstract void run();

    protected void notifySuccess() {
        if (mCallback != null) {
            mCallback.onSuccess(mResponseValues);
        }
    }

    protected void notifyError(int code, String msg) {
        if (mCallback != null) {
            mCallback.onError(code, msg);
        }
    }
}
