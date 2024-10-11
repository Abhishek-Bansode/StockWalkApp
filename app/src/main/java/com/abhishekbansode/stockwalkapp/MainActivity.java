package com.abhishekbansode.stockwalkapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.abhishekbansode.stockwalkapp.api.ApiClient;
import com.abhishekbansode.stockwalkapp.utils.ConfigManager;
import com.abhishekbansode.stockwalkapp.viewmodel.StockViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private StockViewModel stockViewModel;
    private TextInputLayout searchStockEditText;
    private TextView stockNameTextView, stockSymbolTextView, stockPriceTextView, stockPercentageChangeTextView;
    private ProgressBar progressBar;
    private CardView stockCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialize ConfigManager to read values from config.properties
        ConfigManager configManager = new ConfigManager(this);

        String apiKey = configManager.getApiKey();   // Get API Key from config.properties
        String apiHost = configManager.getApiHost(); // Get API Host from config.properties

        // Pass the API Key and Host to the ApiClient
        ApiClient apiClient = new ApiClient();
        apiClient.init(apiKey, apiHost);

        stockViewModel = new ViewModelProvider(this).get(StockViewModel.class);

        searchStockEditText = findViewById(R.id.searchStockEditText);
        Button searchButton = findViewById(R.id.searchButton);
        stockNameTextView = findViewById(R.id.stockNameTextView);
        stockSymbolTextView = findViewById(R.id.stockSymbolTextView);
        stockPriceTextView = findViewById(R.id.stockPriceTextView);
        stockPercentageChangeTextView = findViewById(R.id.stockPercentageChangeTextView);
        progressBar = findViewById(R.id.progressBar);
        stockCard = findViewById(R.id.stockCard);

        searchButton.setOnClickListener(v -> {
            String stockSymbol = Objects.requireNonNull(searchStockEditText.getEditText()).getText().toString().toUpperCase();
            progressBar.setVisibility(View.VISIBLE);
            stockViewModel.fetchStockData(stockSymbol);
        });

        stockViewModel.getStockData().observe(this, stockData -> {
            progressBar.setVisibility(View.GONE);

            if (stockData != null) {
                stockSymbolTextView.setText(String.format("Stock Symbol: %s", stockData.body.symbol));
                stockNameTextView.setText(String.format("Company : %s", stockData.body.companyName));
                stockPriceTextView.setText(String.format("Last Sale Price: %s", stockData.body.primaryData.lastSalePrice));
                stockPercentageChangeTextView.setText(String.format("Percentage Change: %s", stockData.body.primaryData.percentageChange));

                if (stockData.body.primaryData.percentageChange.startsWith("-")) {
                    stockPercentageChangeTextView.setTextColor(ContextCompat.getColor(this, R.color.red));
                } else {
                    stockPercentageChangeTextView.setTextColor(ContextCompat.getColor(this, R.color.green));
                }

                // Make the CardView visible after fetching details
                stockCard.setVisibility(View.VISIBLE);

                Log.d("MainActivity", "StockData - fetched and populated successfully");
            }
        });

        stockViewModel.getErrorMessage().observe(this, errorMessage -> {
            progressBar.setVisibility(View.GONE);
            Log.d("Error", errorMessage);
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        });
    }
}