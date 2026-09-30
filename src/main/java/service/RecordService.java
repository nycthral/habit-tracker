package com.nycthral.habittracker.service;

import com.nycthral.habittracker.dto.RecordResponse;
import com.nycthral.habittracker.entity.Record;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordService {
    private final Map<Long, Record> records = new HashMap<>();
    private long nextId = 1;
    private final HabitService habitService;

    public RecordService(HabitService habitService) {
        this.habitService = habitService;
    }

    public RecordResponse create(Long habitId) {
        habitService.getById(habitId);
        Record record = new Record();
        record.setId(nextId);
        record.setHabitId(habitId);
        record.setDate(LocalDate.now());
        records.put(nextId, record);
        nextId++;
        return new RecordResponse(
                record.getId(),
                record.getHabitId(),
                record.getDate()
        );
    }

    public List<RecordResponse> getByHabitId(Long habitId) {
        habitService.getById(habitId);
        List<RecordResponse> recordResponses = new ArrayList<>();
        for (Record record : records.values()) {
            if (record.getHabitId().equals(habitId)) {
                recordResponses.add(new RecordResponse(
                        record.getId(),
                        record.getHabitId(),
                        record.getDate()
                ));
            }
        }
        return recordResponses;
    }
}
