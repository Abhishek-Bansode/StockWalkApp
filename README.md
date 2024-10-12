# StockWalk App Documentation

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Project Structure](#project-structure)
4. [Workflow](#workflow)
5. [Setup and Installation](#setup-and-installation)
6. [API Integration](#api-integration)
7. [References](#references)

---

## Introduction

The **Stock Walk App** is a simple Android application designed to fetch and display stock data in real-time using the **YH Finance API**. The app allows users to search for stock symbols and view details such as company name, stock price, and percentage change.

---

## Features

- Real-time stock data fetching.
- Displays stock name, price, and percentage change.
- Simple and clean UI using Android's ConstraintLayout, MVVM and Material Design components.
- Robust error handling and loading state management.

---

## Project Structure

```plaintext
StockWalkApp/
├── app/
│   ├── manifests/
│   ├── java/
│   │   └── com.example.stockwalkapp/
│   │       ├── MainActivity.java         # MainActivity for handling the UI logic
│   │       ├── api/
│   │       │   ├── ApiClient.java        # Retrofit setup and API client configuration
│   │       │   └── StockApi.java         # Interface defining the API calls and endpoints
│   │       ├── model/
│   │       │   └── StockResponse.java    # Model class to represent the stock data response from API
│   │       ├── utils/
│   │       │   └── ConfigManager.java    # Utility class for managing config values (API keys)
│   │       ├── viewmodel/
│   │       │   └── StockViewModel.java   # ViewModel class for managing UI-related data for stocks
│   ├── res/
│   │   ├── layout/
│   │   │   └── activity_main.xml         # Layout file for MainActivity
│   │   ├── raw/
│   │   │   └── config.properties         # Contains API keys (API_KEY, API_HOST)
│   │   ├── values/
│   │   │   ├── strings.xml               # String resources for the app
│   │   │   └── colors.xml                # Color resources for the app
│   ├── build.gradle                      # Gradle build script
└── README.md                             # Project documentation

```

---

## Workflow

1. User enters a stock symbol and presses the search button in the **MainActivity**.

2. **MainActivity** sends a request to **StockViewModel** to fetch stock data.

3. **StockViewModel** calls the **ApiClient**, which uses the **StockApi** interface to fetch the data from the YH Finance API.

4. The API response is mapped to a **StockResponse** object.

5. **StockViewModel** updates the **MainActivity** UI with the fetched stock data or shows an error message if the request fails.

This flow ensures separation of concerns and follows the **MVVM pattern**, making it easier to maintain and test.

---

## Setup and Installation

### Prerequisites:

- Android Studio installed
- API Key for YH Finance API
- Steps:

  1. Clone the repository:

     ```bash
     git clone https://github.com/Abhishek-Bansode/StockWalkApp.git
     cd StockWalkApp
     ```

  2. Add your YH Finance **API Key & Host** in config.properties:

     ```plaintext
     X_RAPID_API_KEY=your_api_key_here
     X_RAPID_API_HOST=yahoo-finance15.p.rapidapi.com
     ```

  3. Build the project in Android Studio:

     -> Open the project in Android Studio.

     -> Add Retrofit and OkHttp to build.gradle(module):

     ```gradle
     implementation 'com.squareup.retrofit2:retrofit:2.9.0'
     implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
     implementation 'com.squareup.okhttp3:okhttp:4.9.3'
     ```

  4. Add Network permission in manifests file:

     ```bash
     <uses-permission android:name="android.permission.INTERNET" />
     ```

  5. Sync your project to ensure the **dependencies are downloaded**.
     Sync Gradle files and build the project.

  6. Run the app on an **Emulator** or a **Physical device**.

---

## API Integration

The app uses the YH Finance API from RapidAPI to fetch stock details.

### Key Endpoints Used:

**Stock Details:** Fetch stock details like company name, stock price, and percentage change.

**Endpoint:** /api/v1/markets/quote

**Method:** GET

**Parameters:** ticker & type (e.g., ticker=TSLA&type=STOCKS)

**Headers:** X-RapidAPI-Key & X-RapidAPI-Host

```java
OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                .addHeader("X-RapidAPI-Key", apiKey)
                                .addHeader("X-RapidAPI-Host", apiHost)
                                .build();
                        return chain.proceed(request);
                    }).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://yahoo-finance15.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
```

```java
StockApi api = ApiClient.getClient().create(StockApi.class);
        api.getStockData(ticker, "STOCKS").enqueue(new Callback<StockResponse>()
```

---

## References

1. The **YH Finance API** is used in this app for fetching stock data. More information can be found in their
   [YH Finance API Documentation](https://rapidapi.com/sparior/api/yahoo-finance15).

2. The app follows the **MVVM pattern** for better code separation. Learn more about it [MVVM Pattern in Android](https://developer.android.com/topic/libraries/architecture/viewmodel).

3. The app uses **Retrofit** for making HTTP requests. More information can be found in [Retrofit Documentation](https://square.github.io/retrofit/).
