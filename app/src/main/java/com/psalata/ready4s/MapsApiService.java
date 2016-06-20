package com.psalata.ready4s;

import com.psalata.ready4s.api_results.Results;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Paweł on 18.06.2016.
 */
public interface MapsApiService {
    @GET("maps/api/geocode/json?sensor=true&language=pl-PL")
    Call<Results> getCityDetails(@Query("address") String cityName);
}
