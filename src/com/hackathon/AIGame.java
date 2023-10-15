package com.hackathon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class AIGame {

	public static void main(String[] args) {
        String apiKey = "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";
        
        while (true) {
            String userQuestion = getUserInput("Ask a question (or type 'exit' to quit): ");
            if (userQuestion.equalsIgnoreCase("exit")) {
                break;
            }
            String response = askOpenAI(userQuestion, apiKey);
            System.out.println(response);
        }
    }

    public static String getUserInput(String prompt) {
        try {
            System.out.print(prompt);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String askOpenAI(String question, String apiKey) {
        try {
            String url = "https://api.openai.com/v1/chat/completions";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the HTTP request method to POST
            con.setRequestMethod("POST");

            // Set the request headers
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);

            // Create the request payload
            String payload = "{\"prompt\":\"" + question + "\",\"max_tokens\":500}";

            // Send the POST request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(payload);
            wr.flush();
            wr.close();

            // Get the response
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON response
            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            String completion = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject().get("text").getAsString();
            return completion;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
	
}
