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
    public TagStatsDto create(UUID id) {
        tagStatsReadService.assertTagStatsNotExists(id);
        TagStats tagStats = tagStatsMapper.fromCreateDto(id);

        TagStats savedTagStats = tagStatsRepository.save(tagStats);
        log.info("Создана статистика для тега с id={}", id);
        return tagStatsMapper.toDto(savedTagStats);
    }

    @Override
    @Transactional
    public TagStatsDto incrementUsage(UUID id) {
        TagStats tagStats = tagStatsReadService.getTagStatsByTagId(id);

        tagStats.setUsageCount(tagStats.getUsageCount() + 1);
        TagStats saved = tagStatsRepository.save(tagStats);
        log.info("Обновлён счётчик использования для тега с id={}, usageCount={}", id, saved.getUsageCount());
        return tagStatsMapper.toDto(saved);
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
        Map<UUID, TagStats> tagStatsByTagIds = tagStatsReadService.getTagStatsByTagIds(tagIds);

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
}
