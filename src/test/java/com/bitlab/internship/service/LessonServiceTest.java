package com.bitlab.internship.service;

import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.mappers.LessonMapper;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.model.Lesson;
import com.bitlab.internship.repository.ChapterRepository;
import com.bitlab.internship.repository.LessonRepository;
import com.bitlab.internship.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private LessonMapper lessonMapper;

    @Mock
    private ChapterRepository chapterRepository;

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Test
    void getAllLessonsTest() {
        Chapter chapter = Chapter.builder().id(1L).name("If-else conditions").build();

        Lesson lesson = Lesson.builder()
                .id(1L)
                .name("Lecture for if-else conditions")
                .description("Theoretical foundation of logical branching.")
                .content("In Java, the if statement is used to evaluate a condition.")
                .order(1)
                .chapter(chapter)
                .build();

        LessonDto dto = new LessonDto();
        dto.setId(1L);
        dto.setLessonName("Lecture for if-else conditions");
        dto.setDescription("Theoretical foundation of logical branching.");
        dto.setLessonContent("In Java, the if statement is used to evaluate a condition.");
        dto.setLessonOrder(1);
        dto.setChapterId(1L);

        Mockito.when(lessonRepository.findAll()).thenReturn(List.of(lesson));
        Mockito.when(lessonMapper.toDtoList(List.of(lesson))).thenReturn(List.of(dto));

        List<LessonDto> lessons = lessonService.getAllLessons();

        Assertions.assertNotNull(lessons);
        Assertions.assertNotEquals(0, lessons.size());
        Assertions.assertEquals("Lecture for if-else conditions", lessons.get(0).getLessonName());
        Assertions.assertEquals("Theoretical foundation of logical branching.", lessons.get(0).getDescription());
        Assertions.assertEquals("In Java, the if statement is used to evaluate a condition.", lessons.get(0).getLessonContent());
        Assertions.assertEquals(1, lessons.get(0).getLessonOrder());
        Assertions.assertEquals(1L, lessons.get(0).getChapterId());
    }

    @Test
    void getLessonByIdTest() {
        Lesson lesson = Lesson.builder()
                .id(1L)
                .name("Lecture for if-else conditions")
                .description("Theoretical foundation.")
                .content("Content here")
                .order(1)
                .build();

        LessonDto dto = new LessonDto();
        dto.setId(1L);
        dto.setLessonName("Lecture for if-else conditions");
        dto.setDescription("Theoretical foundation.");
        dto.setLessonContent("Content here");
        dto.setLessonOrder(1);

        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));
        Mockito.when(lessonMapper.toDto(lesson)).thenReturn(dto);

        LessonDto result = lessonService.getByIdLesson(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("Lecture for if-else conditions", result.getLessonName());
        Assertions.assertEquals("Theoretical foundation.", result.getDescription());
        Assertions.assertEquals("Content here", result.getLessonContent());

        Mockito.when(lessonRepository.findById(-1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> lessonService.getByIdLesson(-1L));
    }

    @Test
    void getLessonByChapterIdTest() {
        Lesson lesson = Lesson.builder()
                .id(1L)
                .name("Lecture for if-else conditions")
                .description("Theoretical foundation.")
                .content("Content here")
                .order(1)
                .build();

        LessonDto dto = new LessonDto();
        dto.setId(1L);
        dto.setLessonName("Lecture for if-else conditions");
        dto.setDescription("Theoretical foundation.");
        dto.setLessonContent("Content here");
        dto.setLessonOrder(1);
        dto.setChapterId(1L);

        Mockito.when(lessonRepository.getAllByChapterId(1L)).thenReturn(List.of(lesson));
        Mockito.when(lessonMapper.toDtoList(List.of(lesson))).thenReturn(List.of(dto));

        List<LessonDto> result = lessonService.getLessonByChapterId(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1L, result.get(0).getChapterId());
        Assertions.assertEquals("Lecture for if-else conditions", result.get(0).getLessonName());
    }

    @Test
    void createLessonTest() {
        LessonDto requestDto = new LessonDto();
        requestDto.setLessonName("Practice for if-else conditions");
        requestDto.setDescription("Practical exercises");
        requestDto.setLessonContent("Task 1: Write a program");
        requestDto.setLessonOrder(2);
        requestDto.setChapterId(1L);

        Lesson lesson = Lesson.builder()
                .name("Practice for if-else conditions")
                .description("Practical exercises")
                .content("Task 1: Write a program")
                .order(2)
                .build();

        Lesson savedLesson = Lesson.builder()
                .id(2L)
                .name("Practice for if-else conditions")
                .description("Practical exercises")
                .content("Task 1: Write a program")
                .order(2)
                .build();

        LessonDto responseDto = new LessonDto();
        responseDto.setId(2L);
        responseDto.setLessonName("Practice for if-else conditions");
        responseDto.setDescription("Practical exercises");
        responseDto.setLessonContent("Task 1: Write a program");
        responseDto.setLessonOrder(2);
        responseDto.setChapterId(1L);

        Mockito.when(lessonMapper.toEntity(requestDto)).thenReturn(lesson);
        Mockito.when(lessonRepository.save(lesson)).thenReturn(savedLesson);
        Mockito.when(lessonMapper.toDto(savedLesson)).thenReturn(responseDto);

        LessonDto created = lessonService.createLesson(requestDto);

        Assertions.assertNotNull(created);
        Assertions.assertEquals(2L, created.getId());
        Assertions.assertEquals(requestDto.getLessonName(), created.getLessonName());
        Assertions.assertEquals(requestDto.getDescription(), created.getDescription());
        Assertions.assertEquals(requestDto.getLessonContent(), created.getLessonContent());
        Assertions.assertEquals(requestDto.getLessonOrder(), created.getLessonOrder());
    }

    @Test
    void updateLessonTest() {
        Chapter chapter = Chapter.builder().id(1L).name("If-else conditions").build();
        Lesson existingLesson = Lesson.builder()
                .id(1L)
                .name("Old Lesson")
                .description("Old Desc")
                .content("Old Content")
                .order(1)
                .chapter(chapter)
                .build();

        LessonDto updateDto = new LessonDto();
        updateDto.setId(1L);
        updateDto.setLessonName("Updated Lesson");
        updateDto.setDescription("Updated Description");
        updateDto.setLessonContent("Updated Content");
        updateDto.setLessonOrder(3);
        updateDto.setChapterId(1L);

        Mockito.when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.of(existingLesson));
        Mockito.when(lessonRepository.save(existingLesson)).thenReturn(existingLesson);
        Mockito.when(lessonMapper.toDto(existingLesson)).thenReturn(updateDto);

        LessonDto updated = lessonService.updateLesson(1L, updateDto);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals("Updated Lesson", updated.getLessonName());
        Assertions.assertEquals("Updated Description", updated.getDescription());
        Assertions.assertEquals("Updated Content", updated.getLessonContent());
        Assertions.assertEquals(3, updated.getLessonOrder());

        Mockito.when(chapterRepository.findById(-1L)).thenReturn(Optional.empty());
        updateDto.setChapterId(-1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> lessonService.updateLesson(1L, updateDto));
    }

    @Test
    void deleteLessonTest() {
        Lesson lesson = Lesson.builder().id(1L).name("Lecture for if-else conditions").build();
        Mockito.when(lessonRepository.findById(1L)).thenReturn(Optional.of(lesson));

        boolean deleted = lessonService.deleteLesson(1L);
        Assertions.assertTrue(deleted);
        Mockito.verify(lessonRepository, Mockito.times(1)).delete(lesson);

        Mockito.when(lessonRepository.findById(-1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> lessonService.deleteLesson(-1L));
    }
}