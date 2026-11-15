package com.bitlab.internship.controller;

import com.bitlab.internship.dto.CourseDto;
import com.bitlab.internship.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CourseService courseService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private CourseDto courseDto;

    @BeforeEach
    void setUp() {
        courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setCourseName("Java Developer");
        courseDto.setDescription("Java 2026");
    }

    @Test
    void getAllCoursesTest() throws Exception {
        Mockito.when(courseService.getAllCourse()).thenReturn(List.of(courseDto));

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].courseName").value("Java Developer"))
                .andExpect(jsonPath("$[0].description").value("Java 2026"));

        Mockito.verify(courseService, Mockito.times(1)).getAllCourse();
    }

    @Test
    void getByIdCourseTest() throws Exception {
        Mockito.when(courseService.getByIdCourse(1L)).thenReturn(courseDto);

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.courseName").value("Java Developer"))
                .andExpect(jsonPath("$.description").value("Java 2026"));

        Mockito.verify(courseService, Mockito.times(1)).getByIdCourse(1L);
    }

    @Test
    void createCourseTest() throws Exception {
        Mockito.when(courseService.createCourse(any(CourseDto.class))).thenReturn(courseDto);

        mockMvc.perform(post("/courses/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.courseName").value("Java Developer"))
                .andExpect(jsonPath("$.description").value("Java 2026"));

        Mockito.verify(courseService, Mockito.times(1)).createCourse(any(CourseDto.class));
    }

    @Test
    void updateCourseTest() throws Exception {
        CourseDto updatedDto = new CourseDto();
        updatedDto.setId(1L);
        updatedDto.setCourseName("Java Enterprise Developer");
        updatedDto.setDescription("Updated Java 2026");

        Mockito.when(courseService.updateCourse(eq(1L), any(CourseDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/courses/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.courseName").value("Java Enterprise Developer"))
                .andExpect(jsonPath("$.description").value("Updated Java 2026"));

        Mockito.verify(courseService, Mockito.times(1)).updateCourse(eq(1L), any(CourseDto.class));
    }

    @Test
    void deleteCourseTest() throws Exception {
        Mockito.when(courseService.deleteCourse(1L)).thenReturn(true);

        mockMvc.perform(delete("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        Mockito.verify(courseService, Mockito.times(1)).deleteCourse(1L);
    }
}