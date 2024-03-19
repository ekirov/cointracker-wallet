package com.cointracker.cointrackertakehome.service.impl;

import com.cointracker.cointrackertakehome.dto.TransactionData;
import com.cointracker.cointrackertakehome.service.BlockchainExplorerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class BlockchainExplorerServiceImpl implements BlockchainExplorerService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TransactionData getTransactionData(String address) {
        String url = "https://blockchain.info/rawaddr/" + address;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), TransactionData.class);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            // Handle error appropriately
        }
        return null; // Or throw a custom exception
    }
}
