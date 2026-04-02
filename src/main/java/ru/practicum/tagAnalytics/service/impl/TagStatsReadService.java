package ru.practicum.tagAnalytics.service.impl;

import exception.ConflictException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.tagAnalytics.model.TagStats;
import ru.practicum.tagAnalytics.repository.TagStatsRepository;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagStatsReadService {
    private final TagStatsRepository tagStatsRepository;

    @Transactional(readOnly = true)
    public TagStats getTagStatsByTagId(UUID tagId) {
        return tagStatsRepository.findById(tagId).orElseThrow(() -> {
            log.error("Статистика по тегу с id={} не найдена", tagId);
            return new EntityNotFoundException("Статистика по тегу с id=" + tagId + " не найдена");
        });
    }

    @Transactional(readOnly = true)
    public Map<UUID, TagStats> getTagStatsByTagIds(Set<UUID> tagIds) {
        return tagStatsRepository.findAllByTagIdIn(tagIds).stream()
                .collect(Collectors.toMap(TagStats::getTagId, tagStats -> tagStats));
    }

    @Transactional(readOnly = true)
    public void assertTagStatsNotExists(UUID tagId) {
        if (tagStatsRepository.existsByTagId(tagId)) {
            log.warn("Статистика по тегу с id={} уже существует", tagId);
            throw new ConflictException("Статистика по тегу с id=" + tagId + " уже существует");
        }
    }
}
