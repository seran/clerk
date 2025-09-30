package com.clerk.register.controllers;

import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@RestController
@RequestMapping("/test")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping
    public ResponseEntity<String> test(
            @Parameter(description = "Remote URL", name = "remoteURL", required = true, example = "https://localhost/")
            @RequestParam("remoteURL") String remoteURL
    ) {
        try {
            URL url = new URL(remoteURL);

            logger.info("Initiating to make the call to " + remoteURL);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            if(urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                return ResponseEntity.badRequest().body("Invalid URL");
            }
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1000);
            // Timeout must be 1 second to avoid long hanging requests
            connection.setReadTimeout(1000);

            logger.info("Test call completed.");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String urlString = "";
                String current;

                while((current = in.readLine()) != null) {
                    urlString += current;
                }

                logger.info("Response code " + connection.getResponseCode());
                return ResponseEntity.ok(urlString);
            }

            return ResponseEntity.status(200).body("Unable to fetch.");
        } catch (Exception e) {
            return ResponseEntity.status(204).body(e.getMessage());
        }
    }
}
