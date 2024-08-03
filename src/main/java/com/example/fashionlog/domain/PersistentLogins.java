package com.example.fashionlog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.security.Timestamp;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "persistent_logins")
public class PersistentLogins {


    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Id
    @Column(name = "series", nullable = false, length = 64)
    private String series;

    @Column(name = "token", nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false)
    private Timestamp lastUsed;


}
