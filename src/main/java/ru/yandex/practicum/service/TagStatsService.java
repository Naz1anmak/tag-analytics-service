package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.TagStatsDto;
import ru.yandex.practicum.mapper.TagStatsMapper;
import ru.yandex.practicum.model.TagStats;
import ru.yandex.practicum.repository.TagStatsRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagStatsService {
    private final TagStatsRepository tagStatsRepository;
    private final TagStatsMapper tagStatsMapper;

    @Transactional
    public TagStatsDto incrementUsage(UUID id) {
        OffsetDateTime now = OffsetDateTime.now();
        return tagStatsRepository.findById(id)
                .map(tagStats -> {
                    tagStats.setUsageCount(tagStats.getUsageCount() + 1);
                    tagStats.setLastUsedAt(now);
                    log.info("Статистика тега обновлена {}", tagStats);
                    return tagStatsMapper.toDto(tagStatsRepository.save(tagStats));
                })
                .orElseGet(() -> {
                    TagStats newTagStats = new TagStats();
                    newTagStats.setTagId(id);
                    newTagStats.setUsageCount(1);
                    newTagStats.setLastUsedAt(now);
                    log.info("Новый тег добавлен в статистику {}", newTagStats);
                    return tagStatsMapper.toDto(tagStatsRepository.save(newTagStats));
                });
    }

    @Transactional(readOnly = true)
    public TagStatsDto getTagStats(UUID id) {
        log.info("Запрошена статистика по теги с id={}", id);
        return tagStatsRepository.findById(id)
                .map(tagStats ->
                        new TagStatsDto(tagStats.getUsageCount(), tagStats.getLastUsedAt()))
                .orElseGet(() -> new TagStatsDto(0, null));
    }
}
