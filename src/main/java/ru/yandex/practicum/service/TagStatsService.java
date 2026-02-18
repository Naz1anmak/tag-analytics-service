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
    private final TagStatsReadService tagStatsReadService;

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

    @Transactional(readOnly = true)
    public TagStatsDto getTagStats(UUID id) {
        log.info("Запрошена информация по тегу с id={}", id);
        return tagStatsRepository.findById(id)
                .map(tagStats -> new TagStatsDto(tagStats.getCreatedAt()))
                .orElseGet(() -> new TagStatsDto(null));
    }
}
