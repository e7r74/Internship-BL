package com.bitlab.internship.service.impl;

import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.mappers.LessonMapper;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.model.Lesson;
import com.bitlab.internship.repository.ChapterRepository;
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
    private final ChapterRepository chapterRepository;
    @Override
    public List<LessonDto> getAllLessons() {
        List<Lesson> lessons= lessonRepository.findAll();
        return lessonMapper.toDtoList(lessons);
    }

    @Override
    public LessonDto getByIdLesson(Long id) {
        Lesson lesson= checkLesson(id);
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
    public LessonDto updateLesson(Long id, LessonDto lessonDto) {
        Chapter chapter=null;
        if (lessonDto.getChapterId()!=null){
            chapter= chapterRepository.findById(lessonDto.getChapterId()).orElseThrow(() -> new IllegalArgumentException("Cannot update lesson: Chapter not found with id: " + lessonDto.getChapterId()));
        }
        Lesson updLesson=checkLesson(id);
        updLesson.setName(lessonDto.getLessonName());
        updLesson.setDescription(lessonDto.getDescription());
        updLesson.setContent(lessonDto.getLessonContent());
        updLesson.setOrder(lessonDto.getLessonOrder());
        updLesson.setChapter(chapter);

        return lessonMapper.toDto(lessonRepository.save(updLesson));
    }

    @Override
    public boolean deleteLesson(Long id) {
        Lesson delLesson= checkLesson(id);
        lessonRepository.delete(delLesson);
        return true;
    }

    private Lesson checkLesson(Long id){
        Lesson checkLesson=lessonRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Lesson not found with id: "+id));
        return checkLesson;
    }
}
