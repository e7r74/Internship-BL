package com.bitlab.internship.mapper;

import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.mappers.LessonMapper;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.model.Lesson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class LessonMapperTest {

    @Autowired
    private LessonMapper lessonMapper;

    @Test
    void convertEntityToDtoTest() {
        LocalDateTime now = LocalDateTime.now();
        Chapter chapter = Chapter.builder().id(1L).name("If-else conditions").build();

        Lesson lesson = Lesson.builder()
                .id(1L)
                .name("Lecture for if-else conditions")
                .description("Theoretical foundation of logical branching.")
                .content("In Java, the if statement is used to evaluate a condition.")
                .order(1)
                .chapter(chapter)
                .createdTime(now)
                .updateTime(now)
                .build();

        LessonDto lessonDto = lessonMapper.toDto(lesson);

        Assertions.assertNotNull(lessonDto);
        Assertions.assertNotNull(lessonDto.getId());
        Assertions.assertNotNull(lessonDto.getLessonName());

        Assertions.assertEquals(lesson.getId(), lessonDto.getId());
        Assertions.assertEquals(lesson.getName(), lessonDto.getLessonName());
        Assertions.assertEquals(lesson.getDescription(), lessonDto.getDescription());
        Assertions.assertEquals(lesson.getContent(), lessonDto.getLessonContent());
        Assertions.assertEquals(lesson.getOrder(), lessonDto.getLessonOrder());
        Assertions.assertEquals(lesson.getChapter().getId(), lessonDto.getChapterId());
    }

    @Test
    void convertDtoToEntityTest() {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(1L);
        lessonDto.setLessonName("Lecture for if-else conditions");
        lessonDto.setDescription("Theoretical foundation of logical branching.");
        lessonDto.setLessonContent("In Java, the if statement is used to evaluate a condition.");
        lessonDto.setLessonOrder(1);
        lessonDto.setChapterId(1L);

        Lesson lesson = lessonMapper.toEntity(lessonDto);

        Assertions.assertNotNull(lesson);
        Assertions.assertNotNull(lesson.getId());
        Assertions.assertNotNull(lesson.getName());
        Assertions.assertNotNull(lesson.getChapter());

        Assertions.assertEquals(lessonDto.getId(), lesson.getId());
        Assertions.assertEquals(lessonDto.getLessonName(), lesson.getName());
        Assertions.assertEquals(lessonDto.getDescription(), lesson.getDescription());
        Assertions.assertEquals(lessonDto.getLessonContent(), lesson.getContent());
        Assertions.assertEquals(lessonDto.getLessonOrder(), lesson.getOrder());
        Assertions.assertEquals(lessonDto.getChapterId(), lesson.getChapter().getId());
    }

    @Test
    void convertEntityListToDtoListTest() {
        Chapter chapter = Chapter.builder().id(1L).name("If-else conditions").build();
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(Lesson.builder().id(1L).name("Lecture for if-else conditions").description("Theory").content("Content 1").order(1).chapter(chapter).build());
        lessons.add(Lesson.builder().id(2L).name("Practice for if-else conditions").description("Practice").content("Content 2").order(2).chapter(chapter).build());

        List<LessonDto> lessonDtoList = lessonMapper.toDtoList(lessons);

        Assertions.assertNotNull(lessonDtoList);
        Assertions.assertNotEquals(0, lessonDtoList.size());
        Assertions.assertEquals(lessons.size(), lessonDtoList.size());

        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            LessonDto lessonDto = lessonDtoList.get(i);

            Assertions.assertNotNull(lessonDto);
            Assertions.assertEquals(lesson.getId(), lessonDto.getId());
            Assertions.assertEquals(lesson.getName(), lessonDto.getLessonName());
            Assertions.assertEquals(lesson.getDescription(), lessonDto.getDescription());
            Assertions.assertEquals(lesson.getContent(), lessonDto.getLessonContent());
            Assertions.assertEquals(lesson.getOrder(), lessonDto.getLessonOrder());
            Assertions.assertEquals(lesson.getChapter().getId(), lessonDto.getChapterId());
        }
    }
}