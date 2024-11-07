package com.project.old_system.controller;

import com.project.old_system.model.Client;
import com.project.old_system.payload.ClientNoteRequest;
import com.project.old_system.payload.ClientNoteResponse;
import com.project.old_system.service.OldSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OldSystemController {

    private final OldSystemService oldSystemService;

    @Autowired
    public OldSystemController(OldSystemService oldSystemService) {
        this.oldSystemService = oldSystemService;
    }

    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return oldSystemService.getAllClients();
    }

    @PostMapping("/notes")
    public List<ClientNoteResponse> getClientNotes(@RequestBody ClientNoteRequest clientNoteRequest) {
        return oldSystemService.getClientNotes(clientNoteRequest);
    }
}
