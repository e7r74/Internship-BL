package com.bitlab.internship.service;


import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.model.Lesson;

import java.util.List;

public interface LessonService {
    List<LessonDto> getAllLessons();
    LessonDto getByIdLesson(Long id);
    List<LessonDto> getLessonByChapterId(Long chapterId);
    LessonDto updateLesson(Long id, LessonDto lessonDto);
    LessonDto createLesson(LessonDto lessonDto);
    boolean deleteLesson(Long id);
}
