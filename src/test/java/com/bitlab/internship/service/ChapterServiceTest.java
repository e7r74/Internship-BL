package com.bitlab.internship.service;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.mappers.ChapterMapper;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.model.Course;
import com.bitlab.internship.repository.ChapterRepository;
import com.bitlab.internship.repository.CourseRepository;
import com.bitlab.internship.service.impl.ChapterServiceImpl;
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
class ChapterServiceTest {

    @Mock
    private ChapterRepository chapterRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private ChapterServiceImpl chapterService;

    @Test
    void getAllChaptersTest() {
        Course course = Course.builder().id(1L).name("Java Developer").build();

        Chapter chapter = Chapter.builder()
                .id(1L)
                .name("If-else conditions")
                .description("Learn the basics of branching and decision making in Java.")
                .order(1)
                .course(course)
                .build();

        ChapterDto dto = new ChapterDto();
        dto.setId(1L);
        dto.setChapterName("If-else conditions");
        dto.setDescription("Learn the basics of branching and decision making in Java.");
        dto.setChapterOrder(1);
        dto.setCourseId(1L);

        Mockito.when(chapterRepository.findAll()).thenReturn(List.of(chapter));
        Mockito.when(chapterMapper.toDtoList(List.of(chapter))).thenReturn(List.of(dto));

        List<ChapterDto> chapters = chapterService.getAllChapters();

        Assertions.assertNotNull(chapters);
        Assertions.assertNotEquals(0, chapters.size());
        Assertions.assertEquals("If-else conditions", chapters.get(0).getChapterName());
        Assertions.assertEquals("Learn the basics of branching and decision making in Java.", chapters.get(0).getDescription());
        Assertions.assertEquals(1, chapters.get(0).getChapterOrder());
        Assertions.assertEquals(1L, chapters.get(0).getCourseId());
    }

    @Test
    void getChapterByIdTest() {
        Chapter chapter = Chapter.builder()
                .id(1L)
                .name("If-else conditions")
                .description("Learn the basics of branching and decision making in Java.")
                .order(1)
                .build();

        ChapterDto dto = new ChapterDto();
        dto.setId(1L);
        dto.setChapterName("If-else conditions");
        dto.setDescription("Learn the basics of branching and decision making in Java.");
        dto.setChapterOrder(1);

        Mockito.when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));
        Mockito.when(chapterMapper.toDto(chapter)).thenReturn(dto);

        ChapterDto result = chapterService.getByIdChapter(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("If-else conditions", result.getChapterName());
        Assertions.assertEquals("Learn the basics of branching and decision making in Java.", result.getDescription());

        Mockito.when(chapterRepository.findById(-1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> chapterService.getByIdChapter(-1L));
    }

    @Test
    void getChapterByCourseIdTest() {
        Chapter chapter = Chapter.builder()
                .id(1L)
                .name("If-else conditions")
                .description("Learn the basics of branching and decision making in Java.")
                .order(1)
                .build();

        ChapterDto dto = new ChapterDto();
        dto.setId(1L);
        dto.setChapterName("If-else conditions");
        dto.setDescription("Learn the basics of branching and decision making in Java.");
        dto.setChapterOrder(1);
        dto.setCourseId(1L);

        Mockito.when(chapterRepository.findAllByCourseId(1L)).thenReturn(List.of(chapter));
        Mockito.when(chapterMapper.toDtoList(List.of(chapter))).thenReturn(List.of(dto));

        List<ChapterDto> result = chapterService.getChapterByCourseId(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1L, result.get(0).getCourseId());
        Assertions.assertEquals("If-else conditions", result.get(0).getChapterName());
    }

    @Test
    void createChapterTest() {
        ChapterDto requestDto = new ChapterDto();
        requestDto.setChapterName("If-else conditions");
        requestDto.setDescription("Branching in Java");
        requestDto.setChapterOrder(1);
        requestDto.setCourseId(1L);

        Chapter chapter = Chapter.builder()
                .name("If-else conditions")
                .description("Branching in Java")
                .order(1)
                .build();

        Chapter savedChapter = Chapter.builder()
                .id(1L)
                .name("If-else conditions")
                .description("Branching in Java")
                .order(1)
                .build();

        ChapterDto responseDto = new ChapterDto();
        responseDto.setId(1L);
        responseDto.setChapterName("If-else conditions");
        responseDto.setDescription("Branching in Java");
        responseDto.setChapterOrder(1);
        responseDto.setCourseId(1L);

        Mockito.when(chapterMapper.toEntity(requestDto)).thenReturn(chapter);
        Mockito.when(chapterRepository.save(chapter)).thenReturn(savedChapter);
        Mockito.when(chapterMapper.toDto(savedChapter)).thenReturn(responseDto);

        ChapterDto created = chapterService.createChapter(requestDto);

        Assertions.assertNotNull(created);
        Assertions.assertEquals(1L, created.getId());
        Assertions.assertEquals(requestDto.getChapterName(), created.getChapterName());
        Assertions.assertEquals(requestDto.getDescription(), created.getDescription());
        Assertions.assertEquals(requestDto.getChapterOrder(), created.getChapterOrder());
    }

    @Test
    void updateChapterTest() {
        Course course = Course.builder().id(1L).name("Java Developer").build();
        Chapter existingChapter = Chapter.builder()
                .id(1L)
                .name("Old Name")
                .description("Old Desc")
                .order(1)
                .course(course)
                .build();

        ChapterDto updateDto = new ChapterDto();
        updateDto.setId(1L);
        updateDto.setChapterName("Updated Chapter");
        updateDto.setDescription("Updated Description");
        updateDto.setChapterOrder(2);
        updateDto.setCourseId(1L);

        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(chapterRepository.findById(1L)).thenReturn(Optional.of(existingChapter));
        Mockito.when(chapterRepository.save(existingChapter)).thenReturn(existingChapter);
        Mockito.when(chapterMapper.toDto(existingChapter)).thenReturn(updateDto);

        ChapterDto updated = chapterService.updateChapter(1L, updateDto);

        Assertions.assertNotNull(updated);
        Assertions.assertEquals("Updated Chapter", updated.getChapterName());
        Assertions.assertEquals("Updated Description", updated.getDescription());
        Assertions.assertEquals(2, updated.getChapterOrder());

        Mockito.when(courseRepository.findById(-1L)).thenReturn(Optional.empty());
        updateDto.setCourseId(-1L);
        Assertions.assertThrows(IllegalArgumentException.class, () -> chapterService.updateChapter(1L, updateDto));
    }

    @Test
    void deleteChapterTest() {
        Chapter chapter = Chapter.builder().id(1L).name("If-else conditions").build();
        Mockito.when(chapterRepository.findById(1L)).thenReturn(Optional.of(chapter));

        boolean deleted = chapterService.deleteChapter(1L);
        Assertions.assertTrue(deleted);
        Mockito.verify(chapterRepository, Mockito.times(1)).delete(chapter);

        Mockito.when(chapterRepository.findById(-1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> chapterService.deleteChapter(-1L));
    }
}