package com.mtp.Sync;

import com.mtp.Model.EventPublished;
import com.mtp.Model.User;
import com.mtp.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestRequests {


    @GET(Constants.BASE_URL + "coordinates")
    Call<List<EventPublished>> getAllEventStatic();

    @POST("user")
    Call<User> postUser(@Body User user);

}