package com.example.yemekai;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class chatapiDetails extends AsyncTask<Void, Void, String> {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private boolean isApiRequestMade = false;
    private String recipeName;  // Add this variable to store the selected recipe name
    private OnApiResultListener listener;

    public chatapiDetails(String recipeName, OnApiResultListener listener) {
        this.recipeName = recipeName;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (isApiRequestMade) {
            return null;
        }

        try {
            isApiRequestMade = true;
            return makeApiRequest(API_URL, API_KEY, recipeName);
        } catch (IOException e) {
            Log.e("chatApiDetails", "Error making API request: " + e.getMessage());
            return null;
        }
    }

    public interface OnApiResultListener {
        void onApiResult(String result);
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onApiResult(result);
        }
    }

    private String makeApiRequest(String apiUrl, String apiKey, String recipeName) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            connection.setDoOutput(true);

            String requestData = "{\"model\": \"gpt-3.5-turbo\", " +
                    "\"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"\"}," +
                    "{\"role\": \"user\", \"content\": \"" + recipeName + "detaylı tarifini ver.\"}" +
                    "]}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } finally {
            connection.disconnect();
        }
    }
}