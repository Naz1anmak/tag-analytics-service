package ru.yandex.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.TagStatsDto;
import ru.yandex.practicum.model.TagStats;

@Component
public class TagStatsMapper {

    public TagStatsDto toDto(TagStats tagStats) {
        return new TagStatsDto(
                tagStats.getUsageCount(),
                tagStats.getLastUsedAt()
        );
    }
}
