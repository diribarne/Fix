package com.fix.network;

import com.fix.model.FixUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FixAPI {
    @GET("contacts")
    Call<List<FixUser>> contacts();

    @GET("contacts/{user_id}")
    Call<FixUser> getContact(@Path("user_id") String user_id);
}
