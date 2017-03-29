package com.fix.detail;


import com.fix.model.FixUser;
import com.hannesdorfmann.mosby.mvp.MvpView;

public interface DetailView extends MvpView {

    public void fillData(final FixUser fixUser);
}
