package com.fix.detail;


import com.fix.model.FixUser;
import com.hannesdorfmann.mosby.mvp.MvpView;

public interface DetailView extends MvpView {

    void fillData(final FixUser fixUser);

    void fillAddresss(FixUser fixUser);

    void showError();
}
