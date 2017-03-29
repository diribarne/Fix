package com.fix.detail;


import com.fix.network.FixAPI;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

public class DetailPresenter extends MvpBasePresenter<DetailView> {
    private final FixAPI fixAPI;

    public DetailPresenter(FixAPI fixAPI) {
        this.fixAPI = fixAPI;
    }
}
