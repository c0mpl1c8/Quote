package com.example.quote;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiThread extends Thread {

    MainActivity activity;

    public ApiThread(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        try {
            URL url = new URL("https://dummyjson.com/quotes/random");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            String quoteText = jsonObject.getString("quote");
            String author = jsonObject.getString("author");

            String result = "\"" + quoteText + "\"\n\n— " + author;


            activity.runOnUiThread(() -> {
                activity.TextQuote.setText(result);
            });

        } catch (Exception e) {
            e.printStackTrace();
            activity.runOnUiThread(() -> {
                activity.TextQuote.setText("Error");
            });
        }
    }
}