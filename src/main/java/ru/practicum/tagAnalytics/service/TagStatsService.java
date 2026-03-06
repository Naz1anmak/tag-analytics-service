package ru.practicum.tagAnalytics.service;

import ru.practicum.tagAnalytics.dto.TagStatsDto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface TagStatsService {
    TagStatsDto createIfAbsent(UUID id);

    TagStatsDto getTagStats(UUID id);

    Map<UUID, TagStatsDto> getTagStatsBatch(Set<UUID> tagIds);

    void deleteTagAnalytics(UUID id);
}
