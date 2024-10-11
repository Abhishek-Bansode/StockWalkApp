package com.abhishekbansode.stockwalkapp.utils;

import android.content.Context;

import com.abhishekbansode.stockwalkapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private Context context;

    public ConfigManager(Context context) {
        this.context = context;
    }

    public String getApiKey() {
        return getProperty("X_RAPID_API_KEY");
    }

    public String getApiHost() {
        return getProperty("X_RAPID_API_HOST");
    }

    private String getProperty(String key) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.config);
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
