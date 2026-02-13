package ru.yandex.practicum.dto;

import java.time.OffsetDateTime;

public record TagStatsDto(
        long usageCount,
        OffsetDateTime lastUsedAt
) {
}
