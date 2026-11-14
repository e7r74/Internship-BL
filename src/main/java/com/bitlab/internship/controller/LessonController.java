package com.bitlab.internship.controller;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
@Tag(name = "Lesson Controller", description = "Lessons management APIs")
public class LessonController {
    private final LessonService lessonService;

    @GetMapping
    @Operation(summary = "Get all lessons", description = "Retrieves a list of all available lessons")
    public ResponseEntity<List<LessonDto>> getAllLessons() {
        List<LessonDto> lessonDtos= lessonService.getAllLessons();
        return ResponseEntity.ok(lessonDtos);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get lesson by ID", description = "Retrieves a specific lesson by its unique identifier")
    public ResponseEntity<LessonDto> getByIdLesson(@PathVariable Long id){
        LessonDto lessonDto= lessonService.getByIdLesson(id);
        return ResponseEntity.ok(lessonDto);
    }
    @GetMapping("/chapter/{chapterId}")
    @Operation(summary = "Get lessons by chapter ID", description = "Retrieves a list of all lessons belonging to a specific chapter")
    public ResponseEntity<List<LessonDto>> getLessonsByChapterId(@PathVariable Long chapterId){
        List<LessonDto> lessonDtos= lessonService.getLessonByChapterId(chapterId);
        return ResponseEntity.ok(lessonDtos);
    }
    @PostMapping("/create")
    @Operation(summary = "Create a new lesson", description = "Adds a new lesson record to the database")
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto lessonDto){
        LessonDto createLesson= lessonService.createLesson(lessonDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createLesson);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update lesson", description = "Updates an existing lesson by its ID")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable Long id,
                                                  @RequestBody LessonDto lessonDto){
        LessonDto updLesson= lessonService.updateLesson(id,lessonDto);
        return ResponseEntity.ok(updLesson);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete lesson", description = "Removes a lesson from the database by its ID")
    public ResponseEntity<Boolean> deleteLesson(@PathVariable Long id){
        Boolean deleteLesson= lessonService.deleteLesson(id);
        return ResponseEntity.ok(deleteLesson);
    }
}
