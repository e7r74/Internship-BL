package com.bitlab.internship.service;

import com.bitlab.internship.dto.CourseDto;
import com.bitlab.internship.mappers.CourseMapper;
import com.bitlab.internship.model.Course;
import com.bitlab.internship.repository.CourseRepository;
import com.bitlab.internship.service.impl.CourseServiceImpl;
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
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void getAllCoursesTest() {
        Course course1 = Course.builder().id(1L).name("Java Developer").description("Java 2026").build();
        Course course2 = Course.builder().id(2L).name("GoLang Developer").description("Go 2026").build();
        List<Course> courseList = List.of(course1, course2);

        CourseDto dto1 = new CourseDto();
        dto1.setId(1L);
        dto1.setCourseName("Java Developer");
        dto1.setDescription("Java 2026");

        CourseDto dto2 = new CourseDto();
        dto2.setId(2L);
        dto2.setCourseName("GoLang Developer");
        dto2.setDescription("Go 2026");

        Mockito.when(courseRepository.findAll()).thenReturn(courseList);
        Mockito.when(courseMapper.toDtoList(courseList)).thenReturn(List.of(dto1, dto2));

        List<CourseDto> courses = courseService.getAllCourse();

        Assertions.assertNotNull(courses);
        Assertions.assertNotEquals(0, courses.size());
        Assertions.assertEquals(2, courses.size());

        for (CourseDto courseDto : courses) {
            Assertions.assertNotNull(courseDto);
            Assertions.assertNotNull(courseDto.getId());
            Assertions.assertNotNull(courseDto.getCourseName());
        }
    }

    @Test
    void getCourseByIdTest() {
        Course course = Course.builder().id(1L).name("Java Developer").description("Java 2026").build();
        CourseDto dto = new CourseDto();
        dto.setId(1L);
        dto.setCourseName("Java Developer");
        dto.setDescription("Java 2026");

        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        Mockito.when(courseMapper.toDto(course)).thenReturn(dto);

        CourseDto courseDto = courseService.getByIdCourse(1L);

        Assertions.assertNotNull(courseDto);
        Assertions.assertEquals(1L, courseDto.getId());
        Assertions.assertEquals("Java Developer", courseDto.getCourseName());

        Mockito.when(courseRepository.findById(-1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> courseService.getByIdCourse(-1L));
    }

    @Test
    void createCourseTest() {
        CourseDto requestDto = new CourseDto();
        requestDto.setCourseName("Python Developer");
        requestDto.setDescription("Python 2026");

        Course entityToSave = Course.builder().name("Python Developer").description("Python 2026").build();
        Course savedEntity = Course.builder().id(3L).name("Python Developer").description("Python 2026").build();

        CourseDto responseDto = new CourseDto();
        responseDto.setId(3L);
        responseDto.setCourseName("Python Developer");
        responseDto.setDescription("Python 2026");

        Mockito.when(courseMapper.toEntity(requestDto)).thenReturn(entityToSave);
        Mockito.when(courseRepository.save(entityToSave)).thenReturn(savedEntity);
        Mockito.when(courseMapper.toDto(savedEntity)).thenReturn(responseDto);

        CourseDto createdCourse = courseService.createCourse(requestDto);

        Assertions.assertNotNull(createdCourse);
        Assertions.assertNotNull(createdCourse.getId());
        Assertions.assertNotEquals(0L, createdCourse.getId());
        Assertions.assertEquals(requestDto.getCourseName(), createdCourse.getCourseName());
        Assertions.assertEquals(requestDto.getDescription(), createdCourse.getDescription());
    }

    @Test
    void updateCourseTest() {
        Course existingCourse = Course.builder().id(1L).name("Java Developer").description("Java 2026").build();

        CourseDto updateDto = new CourseDto();
        updateDto.setId(1L);
        updateDto.setCourseName("Java Senior Developer");
        updateDto.setDescription("Updated Java 2026");

        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));
        Mockito.when(courseRepository.save(existingCourse)).thenReturn(existingCourse);
        Mockito.when(courseMapper.toDto(existingCourse)).thenReturn(updateDto);

        CourseDto updatedCourse = courseService.updateCourse(1L, updateDto);

        Assertions.assertNotNull(updatedCourse);
        Assertions.assertEquals(updateDto.getId(), updatedCourse.getId());
        Assertions.assertEquals(updateDto.getCourseName(), updatedCourse.getCourseName());
        Assertions.assertEquals(updateDto.getDescription(), updatedCourse.getDescription());

        Mockito.when(courseRepository.findById(-1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> courseService.updateCourse(-1L, updateDto));
    }

    @Test
    void deleteCourseTest() {
        Course existingCourse = Course.builder().id(1L).name("Java Developer").build();

        Mockito.when(courseRepository.findById(1L)).thenReturn(Optional.of(existingCourse));

        boolean deleted = courseService.deleteCourse(1L);
        Assertions.assertTrue(deleted);
        Mockito.verify(courseRepository, Mockito.times(1)).delete(existingCourse);

        Mockito.when(courseRepository.findById(-1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> courseService.deleteCourse(-1L));
    }
}