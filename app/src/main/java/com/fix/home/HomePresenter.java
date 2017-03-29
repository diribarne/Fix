package com.fix.home;

import com.fix.model.FixUser;
import com.fix.network.FixAPI;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePresenter extends MvpBasePresenter<HomeView> {

    private final FixAPI fixAPI;

    public HomePresenter(final FixAPI changaAPI) {
        this.fixAPI = changaAPI;
    }

    @Override
    public void attachView(HomeView view) {
        super.attachView(view);
    }

    public void getContact() {
        fixAPI.contacts().enqueue(new Callback<List<FixUser>>() {
            @Override
            public void onResponse(Call<List<FixUser>> call, Response<List<FixUser>> response) {
                if (isViewAttached())
                    getView().showContactList(response.body());
            }

            @Override
            public void onFailure(Call<List<FixUser>> call, Throwable t) {
                if (isViewAttached())
                    getView().showError();
            }
        });
    }
}
