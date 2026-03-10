package ru.practicum.tagAnalytics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.tagAnalytics.dto.TagStatsDto;
import ru.practicum.tagAnalytics.service.TagStatsService;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagStatsController {
    private final TagStatsService tagStatsService;

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public TagStatsDto create(@PathVariable UUID id) {
        return tagStatsService.create(id);
    }

    @PatchMapping("/{id}/used")
    public TagStatsDto incrementUsage(@PathVariable UUID id) {
        return tagStatsService.incrementUsage(id);
    }

    @GetMapping("/{id}/stats")
    public TagStatsDto getTagStats(@PathVariable UUID id) {
        return tagStatsService.getTagStats(id);
    }

    @PostMapping("/stats")
    public Map<UUID, TagStatsDto> getTagStatsBatch(@RequestBody Set<UUID> tagIds) {
        return tagStatsService.getTagStatsBatch(tagIds);
    }

    @DeleteMapping("/{id}/used")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagAnalytics(@PathVariable UUID id) {
        tagStatsService.deleteTagAnalytics(id);
    }
}
