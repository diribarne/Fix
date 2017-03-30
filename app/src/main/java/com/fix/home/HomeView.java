package com.fix.home;

import com.fix.model.FixUser;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface HomeView extends MvpView {

    void showContactList(List<FixUser> userList);
    void showError();
    void showProgress(Boolean isShow);
}
