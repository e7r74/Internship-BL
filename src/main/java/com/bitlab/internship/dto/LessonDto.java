package com.bitlab.internship.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonDto {
    private Long id;
    @NotBlank(message = "Lesson name cannot be blank")
    @Size(max = 255, message = "Lesson name must not exceed 255 characters")
    private String lessonName;
    private String description;
    @NotBlank(message = "Lesson content cannot be blank")
    private String lessonContent;
    @Min(value = 1, message = "Lesson order must be greater than or equal to 1")
    private int lessonOrder;
    @NotNull(message = "Chapter ID cannot be null")
    private Long chapterId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
}
