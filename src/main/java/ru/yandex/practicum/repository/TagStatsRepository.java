package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.TagStats;

import java.util.UUID;

public interface TagStatsRepository extends JpaRepository<TagStats, UUID> {
}
