package com.nycthral.habittracker.controller;

import com.nycthral.habittracker.dto.DailyStatsResponse;
import com.nycthral.habittracker.dto.StatsResponse;
import com.nycthral.habittracker.dto.WeekStatsResponse;
import com.nycthral.habittracker.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;

    }

    @GetMapping("/api/habits/{id}/stats")
    public ResponseEntity<StatsResponse> getHabitStats(@PathVariable Long id) {
        StatsResponse statsResponse = statsService.getHabitStats(id);
        return ResponseEntity.ok(statsResponse);
    }

    @GetMapping("/api/stats/daily")
    public ResponseEntity<DailyStatsResponse> getDailyStats() {
        DailyStatsResponse statsResponse = statsService.getDailyStats();
        return ResponseEntity.ok(statsResponse);
    }

    @GetMapping("/api/stats/week")
    public ResponseEntity<WeekStatsResponse> getWeekStats() {
        WeekStatsResponse response = statsService.getWeekStats();
        return ResponseEntity.ok(response);
    }
}
