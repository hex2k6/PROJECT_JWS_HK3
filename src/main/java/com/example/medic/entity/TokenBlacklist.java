package com.example.medic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name =
                "token_blacklist"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenBlacklist {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    @Column(
            unique = true,
            columnDefinition =
                    "TEXT"
    )
    private String token;

    private LocalDateTime
            revokedAt;
}