package com.bitlab.internship.mapper;

import com.bitlab.internship.dto.CourseDto;
import com.bitlab.internship.mappers.CourseMapper;
import com.bitlab.internship.model.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CourseMapperTest {
    @Autowired
    private CourseMapper courseMapper;

    @Test
    void convertEntityToDtoTest() {
        LocalDateTime now = LocalDateTime.now();
        Course course = Course.builder()
                .id(1L)
                .name("Java Developer")
                .description("Java 2026")
                .createdTime(now)
                .updateTime(now)
                .build();

        CourseDto courseDto = courseMapper.toDto(course);

        Assertions.assertNotNull(courseDto);
        Assertions.assertNotNull(courseDto.getId());
        Assertions.assertNotNull(courseDto.getCourseName());

        Assertions.assertEquals(course.getId(), courseDto.getId());
        Assertions.assertEquals(course.getName(), courseDto.getCourseName());
        Assertions.assertEquals(course.getDescription(), courseDto.getDescription());
        Assertions.assertEquals(course.getCreatedTime(), courseDto.getCreatedTime());
        Assertions.assertEquals(course.getUpdateTime(), courseDto.getUpdateTime());
    }
    @Test
    void convertDtoToEntityTest(){
        LocalDateTime now= LocalDateTime.now();
        CourseDto courseDto=new CourseDto();
        courseDto.setId(1L);
        courseDto.setCourseName("Java Developer");
        courseDto.setDescription("Java 2026");
        courseDto.setCreatedTime(now);
        courseDto.setUpdateTime(now);

        Course course=courseMapper.toEntity(courseDto);

        Assertions.assertNotNull(course);
        Assertions.assertNotNull(course.getId());
        Assertions.assertNotNull(course.getName());

        Assertions.assertEquals(courseDto.getId(), course.getId());
        Assertions.assertEquals(courseDto.getCourseName(), course.getName());
        Assertions.assertEquals(courseDto.getDescription(), course.getDescription());
        Assertions.assertEquals(courseDto.getCreatedTime(), course.getCreatedTime());
        Assertions.assertEquals(courseDto.getUpdateTime(), course.getUpdateTime());
    }
    @Test
    void convertEntityListToDtoListTest(){
        List<Course> courses = new ArrayList<>();
        courses.add(Course.builder().id(1L).name("Java Developer").description("Java 2026").build());
        courses.add(Course.builder().id(2L).name("GoLang Developer").description("Go 2026").build());
        courses.add(Course.builder().id(3L).name("Python Developer").description("Python 2026").build());

        List<CourseDto> courseDtos=courseMapper.toDtoList(courses);

        Assertions.assertNotNull(courseDtos);
        Assertions.assertNotEquals(0,courseDtos.size());
        Assertions.assertEquals(courses.size(),courseDtos.size());

        for (int i = 0; i < courses.size(); i++) {
            Course course= courses.get(i);
            CourseDto courseDto= courseDtos.get(i);

            Assertions.assertNotNull(courseDto);
            Assertions.assertNotNull(courseDto.getId());
            Assertions.assertNotNull(courseDto.getCourseName());

            Assertions.assertEquals(course.getId(), courseDto.getId());
            Assertions.assertEquals(course.getName(), courseDto.getCourseName());
            Assertions.assertEquals(course.getDescription(), courseDto.getDescription());
        }
    }
}
