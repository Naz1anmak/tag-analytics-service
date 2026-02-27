package ru.practicum.tagAnalytics.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class TagStats {

    @Id
    @Column(name = "tag_id", nullable = false)
    private UUID tagId;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
}
