package com.fix.network;
import com.fix.model.FixUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FixAPI {
    @GET("contacts")
    Call<List<FixUser>> contacts();
}
