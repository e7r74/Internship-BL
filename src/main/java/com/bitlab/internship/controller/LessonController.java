package com.bitlab.internship.controller;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {
    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<LessonDto>> getAllLessons() {
        List<LessonDto> lessonDtos= lessonService.getAllLessons();
        return ResponseEntity.ok(lessonDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getByIdLesson(@PathVariable Long id){
        LessonDto lessonDto= lessonService.getByIdLesson(id);
        return ResponseEntity.ok(lessonDto);
    }
    @GetMapping("/chapter/{chapterId}")
    public ResponseEntity<List<LessonDto>> getLessonsByChapterId(@PathVariable Long chapterId){
        List<LessonDto> lessonDtos= lessonService.getLessonByChapterId(chapterId);
        return ResponseEntity.ok(lessonDtos);
    }
    @PostMapping("/create")
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto lessonDto){
        LessonDto createLesson= lessonService.createLesson(lessonDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createLesson);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable Long id,
                                                  @RequestBody LessonDto lessonDto){
        LessonDto updLesson= lessonService.updateLesson(id,lessonDto);
        return ResponseEntity.ok(updLesson);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteLesson(@PathVariable Long id){
        Boolean deleteLesson= lessonService.deleteLesson(id);
        return ResponseEntity.ok(deleteLesson);
    }
}
