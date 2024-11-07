package com.project.old_system.service;

import com.project.old_system.logging.AppLogger;
import com.project.old_system.model.Client;
import com.project.old_system.model.ClientNote;
import com.project.old_system.payload.ClientNoteRequest;
import com.project.old_system.payload.ClientNoteResponse;
import com.project.old_system.repository.ClientNoteRepository;
import com.project.old_system.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OldSystemService {

    private final ClientRepository clientRepository;
    private final ClientNoteRepository clientNoteRepository;

    @Autowired
    public OldSystemService(ClientRepository clientRepository, ClientNoteRepository clientNoteRepository) {
        this.clientRepository = clientRepository;
        this.clientNoteRepository = clientNoteRepository;
    }

    public List<Client> getAllClients() {
        AppLogger.logInfo(OldSystemService.class, "Check clients list");
        return clientRepository.findAll();
    }

    public List<ClientNoteResponse> getClientNotes(ClientNoteRequest request) {
        try {
            Client client = clientRepository.findById(request.getClientGuid())
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            if (!client.getAgency().equals(request.getAgency()))
                throw new RuntimeException("Agency does not match");

            LocalDate dateFrom = request.getDateFrom();
            LocalDate dateTo = request.getDateTo();

            List<ClientNote> list = clientNoteRepository.findByClientGuidAndDatetimeBetween(request.getClientGuid(),
                    dateFrom.atStartOfDay(), dateTo.atStartOfDay());
            AppLogger.logInfo(OldSystemService.class, "Check notes for client" + request.getClientGuid());
            return list.stream()
                    .map(clientNote -> new ClientNoteResponse(
                            clientNote.getComments(),
                            clientNote.getGuid(),
                            clientNote.getModifiedDateTime(),
                            clientNote.getClient().getGuid(),
                            clientNote.getDatetime(),
                            clientNote.getLoggedUser().getLogin(),
                            clientNote.getCreatedDateTime()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            AppLogger.logError(OldSystemService.class, e.getMessage());
            return new ArrayList<>();
        }
    }

}