package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.TagStatsDto;
import ru.yandex.practicum.service.TagStatsService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagStatsController {
    private final TagStatsService tagStatsService;

    @PostMapping("/{id}/used")
    @ResponseStatus(HttpStatus.CREATED)
    public TagStatsDto incrementUsage(@PathVariable UUID id) {
        return tagStatsService.createIfAbsent(id);
    }

    @GetMapping("/{id}/stats")
    public TagStatsDto getTagStats(@PathVariable UUID id) {
        return tagStatsService.getTagStats(id);
    }
}
