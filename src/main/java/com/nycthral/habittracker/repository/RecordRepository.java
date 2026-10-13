package com.nycthral.habittracker.repository;

import com.nycthral.habittracker.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {

        List<Record> findByHabitId(Long habitId);
}