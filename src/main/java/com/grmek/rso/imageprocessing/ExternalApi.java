package com.grmek.rso.imageprocessing;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ExternalApi {

    public static String getLabels(String url) {
        try {
            HttpResponse<String> response = Unirest
                    .post("https://image-labeling1.p.rapidapi.com/img/label")
                    .header("x-rapidapi-host", "image-labeling1.p.rapidapi.com")
                    .header("x-rapidapi-key", "52d7ebf1b6msh827f92fe6cc9bb8p183375jsn4e2588d81364")
                    .header("content-type", "application/json")
                    .header("accept", "application/json")
                    .body("{\"url\": \"" + url + "\"}")
                    .asString();

            String[] parts = response.getBody().split("\"");
            return parts[1] + ", " + parts[3] + ", " + parts[5] + ", " + parts[7] + ", " + parts[9];
        }
        catch (Exception e) {
            System.err.println(e);
            return "";
        }
    }
}
