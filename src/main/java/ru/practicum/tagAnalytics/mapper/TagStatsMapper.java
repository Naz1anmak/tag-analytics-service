package ru.practicum.tagAnalytics.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.tagAnalytics.dto.TagStatsDto;
import ru.practicum.tagAnalytics.model.TagStats;

@Component
public class TagStatsMapper {

    public TagStatsDto toDto(TagStats tagStats) {
        return new TagStatsDto(tagStats.getCreatedAt());
    }
}
