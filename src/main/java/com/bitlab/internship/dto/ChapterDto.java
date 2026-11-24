package com.bitlab.internship.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChapterDto {
    private Long id;
    @NotBlank(message = "Chapter name cannot be blank")
    @Size(max = 255, message = "Chapter name must not exceed 255 characters")
    private String chapterName;
    private String description;
    @Min(value = 1, message = "Chapter order must be greater than or equal to 1")
    private int chapterOrder;
    @NotNull(message = "Course ID cannot be null")
    private Long courseId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
}
