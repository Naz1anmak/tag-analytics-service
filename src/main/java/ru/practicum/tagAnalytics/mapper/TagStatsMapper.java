package ru.practicum.tagAnalytics.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.tagAnalytics.dto.TagStatsDto;
import ru.practicum.tagAnalytics.model.TagStats;

import java.util.UUID;

@Component
public class TagStatsMapper {

    public TagStatsDto toDto(TagStats tagStats) {
        return new TagStatsDto(
                tagStats.getUsageCount(),
                tagStats.getCreatedAt(),
                tagStats.getUpdatedAt()
        );
    }

    public TagStats fromCreateDto(UUID id) {
        TagStats tagStats = new TagStats();
        tagStats.setTagId(id);
        tagStats.setUsageCount(0);
        return tagStats;
    }
}
