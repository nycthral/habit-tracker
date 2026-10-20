package com.nycthral.habittracker.service;

import com.nycthral.habittracker.dto.RecordResponse;
import com.nycthral.habittracker.entity.Record;
import org.springframework.stereotype.Service;
import com.nycthral.habittracker.repository.RecordRepository;
import com.nycthral.habittracker.entity.Habit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecordService {
    private static final Logger log = LoggerFactory.getLogger(RecordService.class);

    private final RecordRepository recordRepository;
    private final HabitService habitService;

    public RecordService(RecordRepository recordRepository, HabitService habitService) {
        this.recordRepository = recordRepository;
        this.habitService = habitService;
    }

    public RecordResponse create(Long habitId) {
        log.info("Creating record for habit id: {}", habitId);
        Habit habit = habitService.getEntityById(habitId);
        Record record = new Record();
        record.setHabit(habit);
        record.setDate(LocalDate.now());
        Record saved = recordRepository.save(record);
        return new RecordResponse(
                saved.getId(),
                saved.getHabit().getId(),
                saved.getDate()
        );
    }

    public List<RecordResponse> getByHabitId(Long habitId) {
        habitService.getById(habitId);
        List<RecordResponse> recordResponses = new ArrayList<>();
        for (Record record : recordRepository.findByHabitId(habitId)) {
            recordResponses.add(new RecordResponse(
                    record.getId(),
                    record.getHabit().getId(),
                    record.getDate()
            ));
        }
        return recordResponses;
    }
}
