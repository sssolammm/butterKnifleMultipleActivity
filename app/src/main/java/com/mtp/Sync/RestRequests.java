package com.mtp.Sync;

import com.mtp.Model.StaticLocation;
import com.mtp.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestRequests {


    @GET(Constants.BASE_URL + "getCoordinates")
    Call<List<StaticLocation>> getAllStaticLocation();

    @GET(Constants.BASE_URL + "users")
    Call<List<StaticLocation>> getUsers();

}