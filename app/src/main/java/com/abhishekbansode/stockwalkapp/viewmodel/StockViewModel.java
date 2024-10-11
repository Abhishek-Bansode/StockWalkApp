package com.abhishekbansode.stockwalkapp.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abhishekbansode.stockwalkapp.api.ApiClient;
import com.abhishekbansode.stockwalkapp.api.StockApi;
import com.abhishekbansode.stockwalkapp.model.StockResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockViewModel extends ViewModel {

    private final MutableLiveData<StockResponse> stockData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public void fetchStockData(String ticker) {
        Log.d("StockViewModel", "Fetching stock data for ticker: " + ticker);

        StockApi api = ApiClient.getClient().create(StockApi.class);
        api.getStockData(ticker, "STOCKS").enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(@NonNull Call<StockResponse> call, @NonNull Response<StockResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Log the successful response
                    Log.d("StockViewModel", "API request successful!");

                    // Set data to LiveData
                    stockData.setValue(response.body());

                } else {
                    // Handle non-successful response and log
                    Log.e("StockViewModel", "API request failed. Response Code: " + response.code());
                    Log.e("StockViewModel", "Response Message: " + response.message());

                    errorMessage.setValue("Invalid stock symbol or error occurred.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<StockResponse> call, @NonNull Throwable t) {
                Log.e("StockViewModel", "Network request failed: " + t.getMessage(), t);
                errorMessage.setValue("Network error: " + t.getMessage());
            }
        });
    }

    public LiveData<StockResponse> getStockData() {
        return stockData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
}
