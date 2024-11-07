package com.project.new_system.service;

import com.project.new_system.payload.ClientResponse;
import com.project.new_system.payload.ClientNoteRequest;
import com.project.new_system.payload.ClientNoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class OldSystemClientService {
    //private static final String BASE_URL = "http://localhost:8080/OldSystem";
    private static final String BASE_URL = "http://spring-boot-old-system:8080/OldSystem";

    private final RestTemplate restTemplate;

    @Autowired
    public OldSystemClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ClientResponse> getAllClients() {
        String url = BASE_URL + "/clients";
        ResponseEntity<List<ClientResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ClientResponse>>() {}
        );

        return response.getBody();
    }

    public List<ClientNoteResponse> getClientNotes(ClientNoteRequest clientNoteRequest) {
        String url = BASE_URL + "/notes";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ClientNoteRequest> entity = new HttpEntity<>(clientNoteRequest, headers);
        ResponseEntity<List<ClientNoteResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<ClientNoteResponse>>() {}
        );

        return response.getBody();
    }
}
