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

        return tagStatsReadService.getTagStatsById(id)
                .map(tagStats -> {
                    log.info("Тег с id={} уже добавлен", id);
                    return tagStatsMapper.toDto(tagStats);
                })
                .orElseGet(() -> {
                    TagStats newTagStats = new TagStats();
                    newTagStats.setTagId(id);
                    newTagStats.setCreatedAt(now);

                    TagStats savedTagStats = tagStatsRepository.save(newTagStats);

                    log.info("Добавлен тег с id={}", id);
                    return tagStatsMapper.toDto(savedTagStats);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public TagStatsDto getTagStats(UUID id) {
        log.info("Запрошена информация по тегу с id={}", id);
        return tagStatsRepository.findById(id)
                .map(tagStats -> new TagStatsDto(tagStats.getCreatedAt()))
                .orElseGet(() -> new TagStatsDto(null));
    }

    @Override
    @Transactional(readOnly = true)
    public Map<UUID, TagStatsDto> getTagStatsBatch(Set<UUID> tagIds) {
        log.info("Запрошена информация по тегам с id={}", tagIds);
        return tagStatsRepository.findAllByTagIdIn(tagIds).stream()
                .collect(Collectors.toMap(
                        TagStats::getTagId,
                        tagStats -> new TagStatsDto(tagStats.getCreatedAt())
                ));
    }
}
