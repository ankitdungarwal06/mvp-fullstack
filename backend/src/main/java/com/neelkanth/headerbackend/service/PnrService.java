package com.neelkanth.headerbackend.service;

import com.neelkanth.headerbackend.entity.Pnr;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Arrays;
import java.util.List;

@Service
public class PnrService {

//    private static final List<String> PNR_URLS = Arrays.asList(
//            "https://www.railyatri.in/pnr-status/%s",
//            "https://www.confirmtkt.com/pnr-status/%s",
//            "https://www.ixigo.com/pnr-status-check/%s"
//    );
// Replace with actual API URL, e.g. https://api.example.com/pnr-status/{pnr}
private static final String apiUrlTemplate = "https://www.makemytrip.com/railways/pnrsearch/?pnr=%s";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    public Pnr getPnrStatus(String pnrNumber) throws Exception {
        String apiUrl = String.format(apiUrlTemplate, pnrNumber);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode root = objectMapper.readTree(response.body());

            // Parse JSON fields according to the API response structure
            Pnr pnr = new Pnr();
            pnr.setPnrNumber(pnrNumber);
            pnr.setStatus(root.path("status").asText("UNKNOWN"));
            pnr.setTrainNumber(root.path("trainNumber").asText("N/A"));
            // Set other Pnr fields similarly...

            return pnr;
        } else {
            throw new Exception("API Error: " + response.statusCode());
        }
    }

}
