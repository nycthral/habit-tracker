package com.nycthral.habittracker.service;

import com.nycthral.habittracker.dto.*;
import com.nycthral.habittracker.entity.Habit;
import com.nycthral.habittracker.entity.Record;
import com.nycthral.habittracker.repository.RecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class StatsService {

    @Transactional(readOnly = true)
    public int calculateCurrentStreak(List<Record> records) {
        List<LocalDate> dates = records.stream()
                .map(Record::getDate)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        LocalDate expected = LocalDate.now();
        int streak = 0;

        for (LocalDate date : dates) {
            if (date.equals(expected)) {
                streak++;
                expected = expected.minusDays(1);
            } else {
                break;
            }
        }
        return streak;
    }

    @Transactional(readOnly = true)
    public WeekStatsResponse getWeekStats() {
        List<HabitResponse> habits = habitService.getAll();
        int totalCount = habits.size();
        List<DayStats> week = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate day = LocalDate.now().minusDays(6 - i);
            List<Record> recordsForDay = recordRepository.findByDate(day);
            int completedCount = recordsForDay.size();

            week.add(new DayStats(day, completedCount, totalCount));
        }

        return new WeekStatsResponse(week);
    }

    @Transactional(readOnly = true)
    public int calculateBestStreak(List<Record> records) {
        List<LocalDate> dates = records.stream()
                .map(Record::getDate)
                .sorted()
                .collect(Collectors.toList());

        int current = 0;
        int best = 0;
        LocalDate previous = null;

        for (LocalDate date : dates) {
            if (previous != null && date.equals(previous.plusDays(1))) {
                current++;
            } else {
                current = 1;
            }
            best = Math.max(best, current);
            previous = date;
        }
        return best;
    }

    @Transactional(readOnly = true)
    public DailyStatsResponse getDailyStats() {
        LocalDate today = LocalDate.now();
        List<HabitResponse> habits = habitService.getAll();
        List<Record> todayRecords = recordRepository.findByDate(today);

        List<HabitDailyStatus> statuses = new ArrayList<>();
        for (HabitResponse habit : habits) {
            boolean completed = todayRecords.stream()
                    .anyMatch(r -> r.getHabit().getId().equals(habit.getId()));
            statuses.add(new HabitDailyStatus(habit.getId(), habit.getName(), completed));
        }

        return new DailyStatsResponse(today, statuses);
    }

    @Transactional(readOnly = true)
    public double calculateCompletionRate(List<Record> records, Habit habit) {
        long totalDays = ChronoUnit.DAYS.between(habit.getCreatedAt().toLocalDate(), LocalDate.now());
        double rate = (records.size() * 100.0) / Math.max(totalDays, 1);
        return rate;
    }

    private final HabitService habitService;
    private final RecordRepository recordRepository;

    public StatsService(HabitService habitService, RecordRepository recordRepository) {
        this.habitService = habitService;
        this.recordRepository = recordRepository;
    }

    @Transactional(readOnly = true)
    public StatsResponse getHabitStats(Long habitId) {
        Habit habit = habitService.getEntityById(habitId);
        List<Record> records = recordRepository.findByHabitId(habitId);

        int currentStreak = calculateCurrentStreak(records);
        int bestStreak = calculateBestStreak(records);
        double completionRate = calculateCompletionRate(records, habit);
        long totalCompletions = records.size();

        return new StatsResponse(currentStreak, bestStreak, completionRate, totalCompletions);
    }
}
