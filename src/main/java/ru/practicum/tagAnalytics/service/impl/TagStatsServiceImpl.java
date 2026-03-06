package ru.practicum.tagAnalytics.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.tagAnalytics.dto.TagStatsDto;
import ru.practicum.tagAnalytics.mapper.TagStatsMapper;
import ru.practicum.tagAnalytics.model.TagStats;
import ru.practicum.tagAnalytics.repository.TagStatsRepository;
import ru.practicum.tagAnalytics.service.TagStatsService;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagStatsServiceImpl implements TagStatsService {
    private final TagStatsRepository tagStatsRepository;
    private final TagStatsMapper tagStatsMapper;
    private final TagStatsReadService tagStatsReadService;

    @Override
    @Transactional
    public TagStatsDto createIfAbsent(UUID id) {
        OffsetDateTime now = OffsetDateTime.now();

        return tagStatsReadService.findTagStatsByTagId(id)
                .map(tagStats -> {
                    log.info("Тег с id={} уже добавлен", id);
                    return tagStatsMapper.toDto(tagStats);
                })
                .orElseGet(() -> {
                    TagStats savedTagStats = getSavedTagStats(id, now);

                    log.info("Добавлен тег с id={}", id);
                    return tagStatsMapper.toDto(savedTagStats);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public TagStatsDto getTagStats(UUID id) {
        TagStats tagStats = tagStatsReadService.getTagStatsByTagId(id);
        log.info("Получена информация по тегу с id={}", id);
        return tagStatsMapper.toDto(tagStats);
    }

    @Override
    @Transactional
    public Map<UUID, TagStatsDto> getTagStatsBatch(Set<UUID> tagIds) {
        Map<UUID, TagStats> tagStatsByTagIds = tagStatsReadService.findTagStatsByTagIds(tagIds);

        //Заглушка, потому что в главном сервисе теги не всегда добавляются до запроса
        if (tagStatsByTagIds.size() < tagIds.size()) {
            Set<UUID> missingTagIds = new HashSet<>(tagIds);
            missingTagIds.removeAll(tagStatsByTagIds.keySet());
            log.info("Статистика по тегам с id={} не найдена", missingTagIds);

            OffsetDateTime now = OffsetDateTime.now();
            missingTagIds.forEach(id -> tagStatsByTagIds.put(id, getSavedTagStats(id, now)));
        }

        log.info("Получена информация по тегам с id={}", tagIds);
        return tagStatsByTagIds.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> tagStatsMapper.toDto(entry.getValue())
                ));
    }

    @Override
    @Transactional
    public void deleteTagAnalytics(UUID id) {
        TagStats tagStats = tagStatsReadService.getTagStatsByTagId(id);
        tagStatsRepository.delete(tagStats);
        log.info("Удалена статистика по тегу с id={}", id);
    }

    @Transactional
    public TagStats getSavedTagStats(UUID id, OffsetDateTime now) {
        TagStats newTagStats = new TagStats();
        newTagStats.setTagId(id);
        newTagStats.setCreatedAt(now);

        return tagStatsRepository.save(newTagStats);
    }
}
