package com.project.old_system.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "client_note")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientNote {
    @Id
    private String guid;

    @Column(length = 4000)
    private String comments;

    @Column(nullable = false, name = "createdDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

    @Column(nullable = false, name = "modifiedDateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDateTime;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datetime;

    @ManyToOne
    @JoinColumn(name = "loggedUser", referencedColumnName = "login")
    private User loggedUser;

    @ManyToOne
    @JoinColumn(name = "clientGuid", referencedColumnName = "guid")
    private Client client;
}
