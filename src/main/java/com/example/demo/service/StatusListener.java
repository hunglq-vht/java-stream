package com.example.demo.service;

import com.example.demo.model.ConnectorStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
@EnableScheduling
public class StatusListener {

    @Scheduled(fixedRate = 10000)
    private void getStatus() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8083/connectors" +
                        "?expand=status",
                String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            for (JsonNode node : rootNode) {

                if (node.get("status") == null) {
                    System.out.println("Not found");
                    break;
                }
                ConnectorStatus connectorStatus = objectMapper.readValue(node.get("status").toString(),
                        ConnectorStatus.class);
                connectorStatus.getTasks().forEach(connectorTask -> {
                    if (!connectorTask.getState().equals("RUNNING")
                            && connectorTask.getTrace() != null
                            && !connectorTask.getTrace().contains("ORA-00600")) {
                        System.out.println("Sync all now");
                    }
                });
            }
            System.out.println("Still running...");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
