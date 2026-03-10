package ru.practicum.tagAnalytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.tagAnalytics.model.TagStats;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagStatsRepository extends JpaRepository<TagStats, UUID> {
    List<TagStats> findAllByTagIdIn(Set<UUID> tagIds);

    boolean existsByTagId(UUID tagId);
}
