package ru.practicum.tagAnalytics.dto;

import java.time.OffsetDateTime;

public record TagStatsDto(
        long usageCount,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
