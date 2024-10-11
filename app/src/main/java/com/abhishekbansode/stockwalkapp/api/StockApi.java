package com.abhishekbansode.stockwalkapp.api;

import com.abhishekbansode.stockwalkapp.model.StockResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockApi {

    @GET("/api/v1/markets/quote")
    Call<StockResponse> getStockData(
            @Query("ticker") String ticker,
            @Query("type") String type
    );
}
