package com.bitlab.internship.controller;

import com.bitlab.internship.config.SecurityConfig;
import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.service.ChapterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.oauth2.server.resource.autoconfigure.web.OAuth2ResourceServerWebSecurityAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChapterController.class)
@Import(SecurityConfig.class)
class ChapterControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private JwtDecoder jwtDecoder;
    @MockitoBean
    private ChapterService chapterService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private ChapterDto chapterDto;

    @BeforeEach
    void setUp() {
        chapterDto = new ChapterDto();
        chapterDto.setId(1L);
        chapterDto.setChapterName("If-else conditions");
        chapterDto.setDescription("Learn the basics of branching and decision making in Java.");
        chapterDto.setChapterOrder(1);
        chapterDto.setCourseId(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllChaptersTest() throws Exception {
        Mockito.when(chapterService.getAllChapters()).thenReturn(List.of(chapterDto));

        mockMvc.perform(get("/api/chapters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].chapterName").value("If-else conditions"))
                .andExpect(jsonPath("$[0].chapterOrder").value(1))
                .andExpect(jsonPath("$[0].courseId").value(1L));

        Mockito.verify(chapterService, Mockito.times(1)).getAllChapters();
    }

    @Test
    @WithMockUser(roles = "USER")
    void getByIdChapterTest() throws Exception {
        Mockito.when(chapterService.getByIdChapter(1L)).thenReturn(chapterDto);

        mockMvc.perform(get("/api/chapters/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.chapterName").value("If-else conditions"))
                .andExpect(jsonPath("$.description").value("Learn the basics of branching and decision making in Java."));

        Mockito.verify(chapterService, Mockito.times(1)).getByIdChapter(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    void getChapterByCourseIdTest() throws Exception {
        Mockito.when(chapterService.getChapterByCourseId(1L)).thenReturn(List.of(chapterDto));

        mockMvc.perform(get("/api/chapters/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].courseId").value(1L))
                .andExpect(jsonPath("$[0].chapterName").value("If-else conditions"));

        Mockito.verify(chapterService, Mockito.times(1)).getChapterByCourseId(1L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createChapterTest() throws Exception {
        Mockito.when(chapterService.createChapter(any(ChapterDto.class))).thenReturn(chapterDto);

        mockMvc.perform(post("/api/chapters/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chapterDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.chapterName").value("If-else conditions"))
                .andExpect(jsonPath("$.chapterOrder").value(1));

        Mockito.verify(chapterService, Mockito.times(1)).createChapter(any(ChapterDto.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateChapterTest() throws Exception {
        ChapterDto updatedDto = new ChapterDto();
        updatedDto.setId(1L);
        updatedDto.setChapterName("Loops in Java");
        updatedDto.setDescription("For and While loops");
        updatedDto.setChapterOrder(2);
        updatedDto.setCourseId(1L);

        Mockito.when(chapterService.updateChapter(eq(1L), any(ChapterDto.class))).thenReturn(updatedDto);

        mockMvc.perform(put("/api/chapters/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.chapterName").value("Loops in Java"))
                .andExpect(jsonPath("$.chapterOrder").value(2));

        Mockito.verify(chapterService, Mockito.times(1)).updateChapter(eq(1L), any(ChapterDto.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteChapterTest() throws Exception {
        Mockito.when(chapterService.deleteChapter(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/chapters/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        Mockito.verify(chapterService, Mockito.times(1)).deleteChapter(1L);
    }
}