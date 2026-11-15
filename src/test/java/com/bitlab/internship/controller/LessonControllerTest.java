package com.bitlab.internship.controller;

import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.service.LessonService;
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

@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LessonService lessonService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private LessonDto lessonDto;

    @BeforeEach
    void setUp() {
        lessonDto = new LessonDto();
        lessonDto.setId(1L);
        lessonDto.setLessonName("Lecture for if-else conditions");
        lessonDto.setDescription("Theoretical foundation of logical branching.");
        lessonDto.setLessonContent("In Java, the if statement is used to evaluate a condition.");
        lessonDto.setLessonOrder(1);
        lessonDto.setChapterId(1L);
    }

    @Test
    void getAllLessonsTest() throws Exception {
        Mockito.when(lessonService.getAllLessons()).thenReturn(List.of(lessonDto));

        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].lessonName").value("Lecture for if-else conditions"))
                .andExpect(jsonPath("$[0].lessonOrder").value(1))
                .andExpect(jsonPath("$[0].chapterId").value(1L));

        Mockito.verify(lessonService, Mockito.times(1)).getAllLessons();
    }

    @Test
    void getByIdLessonTest() throws Exception {
        Mockito.when(lessonService.getByIdLesson(1L)).thenReturn(lessonDto);

        mockMvc.perform(get("/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.lessonName").value("Lecture for if-else conditions"))
                .andExpect(jsonPath("$.lessonContent").value("In Java, the if statement is used to evaluate a condition."));

        Mockito.verify(lessonService, Mockito.times(1)).getByIdLesson(1L);
    }

    @Test
    void getLessonsByChapterIdTest() throws Exception {
        Mockito.when(lessonService.getLessonByChapterId(1L)).thenReturn(List.of(lessonDto));

        mockMvc.perform(get("/lessons/chapter/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].chapterId").value(1L))
                .andExpect(jsonPath("$[0].lessonName").value("Lecture for if-else conditions"));

        Mockito.verify(lessonService, Mockito.times(1)).getLessonByChapterId(1L);
    }

    @Test
    void createLessonTest() throws Exception {
        Mockito.when(lessonService.createLesson(any(LessonDto.class))).thenReturn(lessonDto);

        mockMvc.perform(post("/lessons/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lessonDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.lessonName").value("Lecture for if-else conditions"))
                .andExpect(jsonPath("$.lessonOrder").value(1));

        Mockito.verify(lessonService, Mockito.times(1)).createLesson(any(LessonDto.class));
    }

    @Test
    void updateLessonTest() throws Exception {
        LessonDto updatedDto = new LessonDto();
        updatedDto.setId(1L);
        updatedDto.setLessonName("Practice for if-else conditions");
        updatedDto.setDescription("Practical exercises");
        updatedDto.setLessonContent("Task 1: Write a Java program...");
        updatedDto.setLessonOrder(2);
        updatedDto.setChapterId(1L);

        Mockito.when(lessonService.updateLesson(eq(1L), any(LessonDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/lessons/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.lessonName").value("Practice for if-else conditions"))
                .andExpect(jsonPath("$.lessonOrder").value(2));

        Mockito.verify(lessonService, Mockito.times(1)).updateLesson(eq(1L), any(LessonDto.class));
    }

    @Test
    void deleteLessonTest() throws Exception {
        Mockito.when(lessonService.deleteLesson(1L)).thenReturn(true);

        mockMvc.perform(delete("/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        Mockito.verify(lessonService, Mockito.times(1)).deleteLesson(1L);
    }
}