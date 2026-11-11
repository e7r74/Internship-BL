package com.bitlab.internship.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChapterDto {
    private Long id;
    private String chapterName;
    private String description;
    private int chapterOrder;
    private Long courseId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
}
