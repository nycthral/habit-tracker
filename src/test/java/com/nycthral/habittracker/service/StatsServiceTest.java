package com.nycthral.habittracker.service;

import com.nycthral.habittracker.entity.Habit;
import com.nycthral.habittracker.entity.Record;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsServiceTest {

    @Test
    void testCurrentStreak() {
        LocalDate today = LocalDate.now();

        Record r1 = new Record();
        r1.setDate(today);
        Record r2 = new Record();
        r2.setDate(today.minusDays(1));
        Record r3 = new Record();
        r3.setDate(today.minusDays(2));

        StatsService service = new StatsService(null, null);
        int streak = service.calculateCurrentStreak(List.of(r1, r2, r3));

        assertEquals(3, streak);
    }

    @Test
    void testCurrentStreakBroken() {
        LocalDate today = LocalDate.now();

        Record r1 = new Record();
        r1.setDate(today);
        Record r2 = new Record();
        r2.setDate(today.minusDays(2));  // пропуск вчера

        StatsService service = new StatsService(null, null);
        int streak = service.calculateCurrentStreak(List.of(r1, r2));

        assertEquals(1, streak);
    }

    @Test
    void testBestStreak() {
        LocalDate today = LocalDate.now();

        // Цепочка 1: 3 дня (сегодня, вчера, позавчера)
        Record r1 = new Record(); r1.setDate(today);
        Record r2 = new Record(); r2.setDate(today.minusDays(1));
        Record r3 = new Record(); r3.setDate(today.minusDays(2));

        // Пропуск 3, 4 дня. Цепочка 2: 2 дня (5 и 6 дней назад)
        Record r4 = new Record(); r4.setDate(today.minusDays(5));
        Record r5 = new Record(); r5.setDate(today.minusDays(6));

        StatsService service = new StatsService(null, null);
        int best = service.calculateBestStreak(List.of(r1, r2, r3, r4, r5));

        assertEquals(3, best);
    }

    @Test
    void testCompletionRate() {
        LocalDate today = LocalDate.now();

        Habit habit = new Habit();
        habit.setCreatedAt(today.minusDays(10).atStartOfDay());  // создана 10 дней назад

        Record r1 = new Record(); r1.setDate(today);
        Record r2 = new Record(); r2.setDate(today.minusDays(1));
        Record r3 = new Record(); r3.setDate(today.minusDays(2));
        Record r4 = new Record(); r4.setDate(today.minusDays(3));
        Record r5 = new Record(); r5.setDate(today.minusDays(4));

        StatsService service = new StatsService(null, null);
        double rate = service.calculateCompletionRate(List.of(r1, r2, r3, r4, r5), habit);

        assertEquals(50.0, rate, 0.01);
    }
}