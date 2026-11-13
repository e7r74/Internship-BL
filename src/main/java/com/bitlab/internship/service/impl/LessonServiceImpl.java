package com.bitlab.internship.service.impl;

import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.mappers.LessonMapper;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.model.Lesson;
import com.bitlab.internship.repository.ChapterRepository;
import com.bitlab.internship.repository.LessonRepository;
import com.bitlab.internship.service.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;
    private final ChapterRepository chapterRepository;
    @Override
    public List<LessonDto> getAllLessons() {
        log.info("Fetching all lessons");
        List<Lesson> lessons= lessonRepository.findAll();
        return lessonMapper.toDtoList(lessons);
    }

    @Override
    public LessonDto getByIdLesson(Long id) {
        log.info("Fetching lesson by id: {}",id);
        Lesson lesson= checkLesson(id);
        return lessonMapper.toDto(lesson);
    }

    @Override
    public List<LessonDto> getLessonByChapterId(Long chapterId) {
        log.info("Fetching lessons by chapterId: {}",chapterId);
       List<Lesson> lessons=lessonRepository.getAllByChapterId(chapterId);
       return lessonMapper.toDtoList(lessons);
    }

    @Override
    public LessonDto createLesson(LessonDto lessonDto) {
        log.info("Create new lesson");
        Lesson lesson= lessonMapper.toEntity(lessonDto);
        log.debug("Saving lesson object: {}",lesson);
        return lessonMapper.toDto( lessonRepository.save(lesson));
    }

    @Override
    public LessonDto updateLesson(Long id, LessonDto lessonDto) {
        log.info("Update lesson with id: {}",id);
        Chapter chapter=null;
        if (lessonDto.getChapterId()!=null){
            chapter= chapterRepository.findById(lessonDto.getChapterId())
                                        .orElseThrow(() ->{
                                            log.error("Cannot update chapter: Chapter not found with id: {}",lessonDto.getChapterId());
                                            return new IllegalArgumentException("Cannot update lesson: Chapter not found with id: " + lessonDto.getChapterId());
                                        });
        }
        Lesson updLesson=checkLesson(id);
        updLesson.setName(lessonDto.getLessonName());
        updLesson.setDescription(lessonDto.getDescription());
        updLesson.setContent(lessonDto.getLessonContent());
        updLesson.setOrder(lessonDto.getLessonOrder());
        updLesson.setChapter(chapter);
        log.debug("Updated lesson object: {}", updLesson);
        return lessonMapper.toDto(lessonRepository.save(updLesson));
    }

    @Override
    public boolean deleteLesson(Long id) {
        log.info("Deleting lesson with id: {}",id);
        Lesson delLesson= checkLesson(id);
        lessonRepository.delete(delLesson);
        return true;
    }

    private Lesson checkLesson(Long id){
        return lessonRepository.findById(id)
                                .orElseThrow(()->{
                                    log.info("Lesson not found with id: {}",id);
                                    return new IllegalArgumentException("Lesson not found with id: "+id);
                                });

    }
}
