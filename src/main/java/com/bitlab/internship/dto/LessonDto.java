package com.bitlab.internship.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonDto {
    private Long id;
    private String lessonName;
    private String description;
    private String lessonContent;
    private int lessonOrder;
    private Long chapterId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
}
