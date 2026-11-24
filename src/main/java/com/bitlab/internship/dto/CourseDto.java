package com.bitlab.internship.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseDto {
    private Long id;
    @NotBlank(message = "Course name cannot be blank")
    @Size(max = 255, message = "Course name must not exceed 255 characters")
    private String courseName;
    private String description;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
}
