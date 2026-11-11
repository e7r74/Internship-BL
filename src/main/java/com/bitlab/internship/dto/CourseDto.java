package com.bitlab.internship.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseDto {
    private Long id;
    private String CourseName;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
}
