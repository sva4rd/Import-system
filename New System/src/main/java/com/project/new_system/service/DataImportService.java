package com.project.new_system.service;

import com.project.new_system.logging.AppLogger;
import com.project.new_system.model.CompanyUser;
import com.project.new_system.model.OldGuid;
import com.project.new_system.model.PatientNote;
import com.project.new_system.model.PatientProfile;
import com.project.new_system.payload.ClientNoteRequest;
import com.project.new_system.payload.ClientNoteResponse;
import com.project.new_system.payload.ClientResponse;
import com.project.new_system.repository.CompanyUserRepository;
import com.project.new_system.repository.OldGuidRepository;
import com.project.new_system.repository.PatientNoteRepository;
import com.project.new_system.repository.PatientProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DataImportService {

    private final PatientProfileRepository patientProfileRepository;
    private final OldGuidRepository oldGuidRepository;
    private final CompanyUserRepository companyUserRepository;
    private final PatientNoteRepository patientNoteRepository;
    private final OldSystemClientService oldSystemClientService;

    private final LocalDate startDate = LocalDate.of(2020, 1, 1);

    @Autowired
    public DataImportService(OldSystemClientService oldSystemClientService,
                             PatientProfileRepository patientProfileRepository,
                             OldGuidRepository oldGuidRepository,
                             CompanyUserRepository companyUserRepository,
                             PatientNoteRepository patientNoteRepository) {
        this.oldSystemClientService = oldSystemClientService;
        this.patientProfileRepository = patientProfileRepository;
        this.oldGuidRepository = oldGuidRepository;
        this.companyUserRepository = companyUserRepository;
        this.patientNoteRepository = patientNoteRepository;
    }


    //@Scheduled(cron = "0 15 0/2 * * ?")
    @Scheduled(cron = "*/30 * * * * ?")
    public void importData() {
        List<ClientResponse> clients = oldSystemClientService.getAllClients();
        List<PatientProfile> patients = patientProfileRepository.findByStatusIdIn(Arrays.asList(200, 210, 230));
        ImportStatistic statistic = new ImportStatistic();

        for (PatientProfile patient : patients) {
            AppLogger.logInfo(DataImportService.class, "Check updates for patient with id = " + patient.getId());
            List<OldGuid> guids = oldGuidRepository.findByPatientProfileId(patient.getId());
            for (OldGuid guid : guids) {
                try{
                    String agency = findAgencyForGuid(clients, guid);
                    ClientNoteRequest request = createClientNoteRequest(guid, agency);
                    List<ClientNoteResponse> oldNotes = oldSystemClientService.getClientNotes(request);
                    processOldNotes(oldNotes, patient, statistic);
                } catch (Exception e){
                    AppLogger.logError(DataImportService.class, e.getMessage());
                }
            }
        }
        AppLogger.logInfo(DataImportService.class,
                "\n\tImport started at " + statistic.getImportDateTimeStart() +
                        "\n\tImport ended at " + LocalDateTime.now() +
                        "\n\tUpdated - " + statistic.getUpdatedCount() +
                        "\n\tCreated - " + statistic.getCreatedCount());
    }

    private String findAgencyForGuid(List<ClientResponse> clients, OldGuid guid) {
        return clients.stream()
                .filter(client -> client.getGuid().equals(guid.getOldClientGuid()))
                .map(ClientResponse::getAgency)
                .findFirst()
                .orElse(null);
    }

    private ClientNoteRequest createClientNoteRequest(OldGuid guid, String agency) {
        return new ClientNoteRequest(guid.getOldClientGuid(), agency, startDate, LocalDate.now());
    }

    private void processOldNotes(List<ClientNoteResponse> oldNotes, PatientProfile patient,
                                 ImportStatistic statistic) {
        for (ClientNoteResponse note : oldNotes) {
            CompanyUser user = getOrCreateCompanyUser(note.getLoggedUser());
            Optional<PatientNote> systemNoteOptional = patientNoteRepository.findByOldGuid(note.getGuid());

            if (systemNoteOptional.isPresent() &&
                    updatePatientNoteIfNeeded(systemNoteOptional.get(), note, user)) {
                statistic.incrementUpdated();
            } else if (systemNoteOptional.isEmpty()){
                createAndSaveNewPatientNote(note, patient, user);
                statistic.incrementCreated();
            }
        }
    }

    private CompanyUser getOrCreateCompanyUser(String loggedUser) {
        return companyUserRepository.findByLogin(loggedUser)
                .orElseGet(() -> {
                    CompanyUser newUser = new CompanyUser(loggedUser);
                    AppLogger.logInfo(DataImportService.class, "Imported user with login = " + loggedUser);
                    return companyUserRepository.save(newUser);
                });
    }

    private boolean updatePatientNoteIfNeeded(PatientNote systemNote, ClientNoteResponse note, CompanyUser user) {
        if (systemNote.getLastModifiedDateTime().isBefore(note.getModifiedDateTime())) {
            systemNote.setNote(note.getComments());
            systemNote.setLastModifiedDateTime(note.getModifiedDateTime());
            systemNote.setCreatedDateTime(note.getCreatedDateTime());
            systemNote.setLastModifiedByUser(user);
            patientNoteRepository.save(systemNote);
            AppLogger.logInfo(DataImportService.class, "Updated note with id = " + systemNote.getId());
            return true;
        }
        return false;
    }

    private void createAndSaveNewPatientNote(ClientNoteResponse note, PatientProfile patient, CompanyUser user) {
        PatientNote newPatientNote = new PatientNote();
        newPatientNote.setNote(note.getComments());
        newPatientNote.setOldGuid(note.getGuid());
        newPatientNote.setLastModifiedDateTime(note.getModifiedDateTime());
        newPatientNote.setCreatedDateTime(note.getCreatedDateTime());
        newPatientNote.setLastModifiedByUser(user);
        newPatientNote.setCreatedByUser(user);
        newPatientNote.setPatient(patient);
        patientNoteRepository.save(newPatientNote);
        AppLogger.logInfo(DataImportService.class, "Created note with id = " + newPatientNote.getId());
    }

}

