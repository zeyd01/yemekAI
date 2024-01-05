package com.example.yemekai;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class chatapi extends AsyncTask<Void, Void, String> {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private boolean isApiRequestMade = false;
    private List<ItemL> items;
    private String editTextContent; // Add this variable to store EditText content
    private OnApiResultListener listener;

    public chatapi(List<ItemL> items, String editTextContent, OnApiResultListener listener) {
        this.items = items;
        this.editTextContent = editTextContent;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (isApiRequestMade) {

            return null;
        }

        try {
            isApiRequestMade = true;
            return makeApiRequest(API_URL, API_KEY, items, editTextContent);
        } catch (IOException e) {
            Log.e("chatapi", "Error making API request: " + e.getMessage());
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

    private String makeApiRequest(String apiUrl, String apiKey, List<ItemL> items, String editTextContent) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            // Set the request method to POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);

            // Enable input/output streams
            connection.setDoOutput(true);

            // Create JSON request data
            String requestData = "{\"model\": \"gpt-3.5-turbo\", " +
                    "\"messages\": [" +
                    "{\"role\": \"system\", \"content\": \"\"}," +
                    "{\"role\": \"user\", \"content\": \"Benim " + stringifyItems(items) + "var. " + editTextContent + "yemekler öner.Bu yemeklerin adını ve kısa bir özetini teker teker liste şeklinde istiyorum\"}" +
                    "]}";

            // Send the request
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } finally {
            // Disconnect to release resources
            connection.disconnect();
        }
    }

    private String stringifyItems(List<ItemL> items) {
        if (items == null) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (ItemL item : items) {
            stringBuilder.append(item.toString()).append(", ");
        }

        // Remove the trailing comma and space
        if (stringBuilder.length() > 2) {
            stringBuilder.setLength(stringBuilder.length() - 2);
        }

        return stringBuilder.toString();
    }

}