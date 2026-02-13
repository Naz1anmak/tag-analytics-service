package ru.yandex.practicum.model;

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
    private UUID tagId;

    private long usageCount;

    private OffsetDateTime lastUsedAt;
}
