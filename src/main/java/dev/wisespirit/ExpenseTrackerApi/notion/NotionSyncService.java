package dev.wisespirit.ExpenseTrackerApi.notion;

import dev.wisespirit.ExpenseTrackerApi.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotionSyncService {
    private final RestClient restClient;
    private final NotionConfigProperties notionConfigProperties;

    public NotionSyncService(RestClient restClient, NotionConfigProperties notionConfigProperties) {
        this.restClient = restClient;
        this.notionConfigProperties = notionConfigProperties;
    }

    public void syncTransactionToNotion(Transaction transaction){

    }
}
