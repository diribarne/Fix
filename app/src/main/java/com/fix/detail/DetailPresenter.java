package com.fix.detail;


import com.fix.model.FixUser;
import com.fix.network.FixAPI;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter extends MvpBasePresenter<DetailView> {
    private final FixAPI fixAPI;

    public DetailPresenter(FixAPI fixAPI) {
        this.fixAPI = fixAPI;
    }


    public void getContact(String contactID) {
        fixAPI.getContact(contactID).enqueue(new Callback<FixUser>() {
            @Override
            public void onResponse(Call<FixUser> call, Response<FixUser> response) {
                if (isViewAttached())
                    getView().fillAddresss(response.body());
            }

            @Override
            public void onFailure(Call<FixUser> call, Throwable t) {
                if (isViewAttached())
                    getView().showError();
            }
        });
    }
}
