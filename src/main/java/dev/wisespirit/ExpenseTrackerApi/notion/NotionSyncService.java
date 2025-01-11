package dev.wisespirit.ExpenseTrackerApi.notion;

import dev.wisespirit.ExpenseTrackerApi.model.Transaction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotionSyncService {
    private final RestClient restClient;
    private final NotionConfigProperties notionConfigProperties;

    public NotionSyncService(RestClient restClient, NotionConfigProperties notionConfigProperties) {
        this.restClient = restClient;
        this.notionConfigProperties = notionConfigProperties;
    }

    public void syncTransactionToNotion(Transaction transaction) {
        String uri = notionConfigProperties.apiUrl() + "/pages";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("parent", Map.of("database_id", notionConfigProperties.databaseId()));

        Map<String, Object> properties = new HashMap<>();

        properties.put("ID", Map.of(
                "title", List.of(Map.of(
                        "text", Map.of("content", transaction.getId().toString())
                ))
        ));

        properties.put("Amount", Map.of(
                "number", transaction.getAmount().doubleValue()
        ));

        if (transaction.getDate() != null) {
            properties.put("Date", Map.of(
                    "date", Map.of(
                            "start", transaction.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE)
                    )
            ));
        }

        if (transaction.getType() != null) {
            properties.put("Type", Map.of(
                    "select", Map.of("name", transaction.getType().name())
            ));
        }

        if (transaction.getStatus() != null) {
            properties.put("Status", Map.of(
                    "select", Map.of("name", transaction.getStatus().name())
            ));
        }

        if (transaction.getMoneyType() != null) {
            properties.put("Money Type", Map.of(
                    "select", Map.of("name", transaction.getMoneyType().name())
            ));
        }

        properties.put("Client ID", Map.of(
                "number", transaction.getClientId()
        ));

        properties.put("Service ID", Map.of(
                "number", transaction.getServiceId()
        ));

        if (transaction.getNote() != null && !transaction.getNote().isEmpty()) {
            properties.put("Note", Map.of(
                    "rich_text", List.of(Map.of(
                            "text", Map.of("content", transaction.getNote())
                    ))
            ));
        }

        if (transaction.getFileUrl() != null && !transaction.getFileUrl().isEmpty()) {
            properties.put("File URL", Map.of(
                    "url", transaction.getFileUrl()
            ));
        }

        requestBody.put("properties", properties);

        try {
            ResponseEntity<Map> response = restClient
                    .post()
                    .uri(uri)
                    .headers(headers -> {
                                headers.set("Authorization", notionConfigProperties.authToken());
                                headers.set("Notion-Version", notionConfigProperties.apiVersion());
                                headers.setContentType(MediaType.APPLICATION_JSON);
                            }
                    )
                    .body(requestBody)
                    .retrieve()
                    .toEntity(Map.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Failed to sync transaction to Notion. Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error syncing transaction to Notion", e);
        }
    }
}
