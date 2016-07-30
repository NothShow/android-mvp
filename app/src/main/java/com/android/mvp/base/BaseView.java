package com.android.mvp.base;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
