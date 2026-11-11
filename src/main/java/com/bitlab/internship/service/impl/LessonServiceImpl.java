package com.bitlab.internship.service.impl;

import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.mappers.LessonMapper;
import com.bitlab.internship.model.Lesson;
import com.bitlab.internship.repository.LessonRepository;
import com.bitlab.internship.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    @Override
    public List<LessonDto> getAllLessons() {
        List<Lesson> lessons= lessonRepository.findAll();
        return lessonMapper.toDtoList(lessons);
    }

    @Override
    public LessonDto getByIdLesson(Long id) {
        Lesson lesson= lessonRepository.findById(id).orElseThrow(()->new RuntimeException("Lesson not found with id: "+id));
        return lessonMapper.toDto(lesson);
    }

    @Override
    public List<LessonDto> getLessonByChapterId(Long chapterId) {
       List<Lesson> lessons=lessonRepository.getAllByChapterId(chapterId);
       return lessonMapper.toDtoList(lessons);
    }

    @Override
    public LessonDto createLesson(LessonDto lessonDto) {
        Lesson lesson= lessonMapper.toEntity(lessonDto);
        return lessonMapper.toDto( lessonRepository.save(lesson));
    }

    @Override
    public boolean deleteLesson(Long id) {
        Lesson delLesson=lessonRepository.findById(id).orElseThrow(()->new RuntimeException("Lesson not found with id: "+id));
        lessonRepository.delete(delLesson);
        return true;
    }
}
