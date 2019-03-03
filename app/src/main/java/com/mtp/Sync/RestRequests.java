package com.mtp.Sync;

import com.mtp.Model.EventStatic;
import com.mtp.Utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestRequests {


    @GET(Constants.BASE_URL + "coordinates")
    Call<List<EventStatic>> getAllEventStatic();

}