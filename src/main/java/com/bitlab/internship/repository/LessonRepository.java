package com.bitlab.internship.repository;

import com.bitlab.internship.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> getAllByChapterId(Long chapterId);
}
