package com.bitlab.internship.mapper;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.mappers.ChapterMapper;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.model.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ChapterMapperTest {

    @Autowired
    private ChapterMapper chapterMapper;

    @Test
    void convertEntityToDtoTest() {
        LocalDateTime now = LocalDateTime.now();
        Course course = Course.builder().id(1L).name("Java Developer").build();

        Chapter chapter = Chapter.builder()
                .id(1L)
                .name("If-else conditions")
                .description("Learn the basics of branching and decision making in Java.")
                .order(1)
                .course(course)
                .createdTime(now)
                .updateTime(now)
                .build();

        ChapterDto chapterDto = chapterMapper.toDto(chapter);

        Assertions.assertNotNull(chapterDto);
        Assertions.assertNotNull(chapterDto.getId());
        Assertions.assertNotNull(chapterDto.getChapterName());

        Assertions.assertEquals(chapter.getId(), chapterDto.getId());
        Assertions.assertEquals(chapter.getName(), chapterDto.getChapterName());
        Assertions.assertEquals(chapter.getDescription(), chapterDto.getDescription());
        Assertions.assertEquals(chapter.getOrder(), chapterDto.getChapterOrder());
        Assertions.assertEquals(chapter.getCourse().getId(), chapterDto.getCourseId());
    }

    @Test
    void convertDtoToEntityTest() {
        ChapterDto chapterDto = new ChapterDto();
        chapterDto.setId(1L);
        chapterDto.setChapterName("If-else conditions");
        chapterDto.setDescription("Learn the basics of branching and decision making in Java.");
        chapterDto.setChapterOrder(1);
        chapterDto.setCourseId(1L);

        Chapter chapter = chapterMapper.toEntity(chapterDto);

        Assertions.assertNotNull(chapter);
        Assertions.assertNotNull(chapter.getId());
        Assertions.assertNotNull(chapter.getName());
        Assertions.assertNotNull(chapter.getCourse());

        Assertions.assertEquals(chapterDto.getId(), chapter.getId());
        Assertions.assertEquals(chapterDto.getChapterName(), chapter.getName());
        Assertions.assertEquals(chapterDto.getDescription(), chapter.getDescription());
        Assertions.assertEquals(chapterDto.getChapterOrder(), chapter.getOrder());
        Assertions.assertEquals(chapterDto.getCourseId(), chapter.getCourse().getId());
    }

    @Test
    void convertEntityListToDtoListTest() {
        Course course = Course.builder().id(1L).name("Java Developer").build();
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(Chapter.builder().id(1L).name("If-else conditions").description("Branching").order(1).course(course).build());
        chapters.add(Chapter.builder().id(2L).name("Loops in Java").description("For and While").order(2).course(course).build());

        List<ChapterDto> chapterDtoList = chapterMapper.toDtoList(chapters);

        Assertions.assertNotNull(chapterDtoList);
        Assertions.assertNotEquals(0, chapterDtoList.size());
        Assertions.assertEquals(chapters.size(), chapterDtoList.size());

        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            ChapterDto chapterDto = chapterDtoList.get(i);

            Assertions.assertNotNull(chapterDto);
            Assertions.assertEquals(chapter.getId(), chapterDto.getId());
            Assertions.assertEquals(chapter.getName(), chapterDto.getChapterName());
            Assertions.assertEquals(chapter.getDescription(), chapterDto.getDescription());
            Assertions.assertEquals(chapter.getOrder(), chapterDto.getChapterOrder());
            Assertions.assertEquals(chapter.getCourse().getId(), chapterDto.getCourseId());
        }
    }
}