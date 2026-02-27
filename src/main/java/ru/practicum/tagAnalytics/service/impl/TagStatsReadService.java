package ru.practicum.tagAnalytics.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.tagAnalytics.model.TagStats;
import ru.practicum.tagAnalytics.repository.TagStatsRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagStatsReadService {
    private final TagStatsRepository tagStatsRepository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public Optional<TagStats> getTagStatsById(UUID id) {
        return tagStatsRepository.findById(id);
    }
}
