package salsabilaaulia.tonemessagechanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import salsabilaaulia.tonemessagechanger.dto.MessageRequest;

@Service
public class ClientService {
    @Autowired
    MessageService messageService;

    private final RestClient restClient;

    public ClientService() {
        this.restClient = RestClient.create();
    }

    public String getResponse(MessageRequest messageRequest) {
        String response = restClient.post()
                .uri("http://localhost:8080/api/message/change-tone")
                .body(messageRequest)
                .retrieve()
                .body(String.class);

        return response;
    }
}
