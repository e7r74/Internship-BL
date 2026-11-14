package com.bitlab.internship.controller;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.service.ChapterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chapters")
@Tag(name = "Chapter controller", description = "Course chapters management APIs")
public class ChapterController {
    private final ChapterService chapterService;

    @GetMapping
    @Operation(summary = "Get all chapters", description = "Retrieves a list of all course chapters")
    public ResponseEntity<List<ChapterDto>> getAllChapters(){
        List<ChapterDto> chapterDtos= chapterService.getAllChapters();
        return ResponseEntity.ok(chapterDtos);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get chapter by id", description = "Retrieves a specific chapter by its unique identifier")
    public ResponseEntity<ChapterDto> getByIdChapter(@PathVariable Long id){
        ChapterDto chapterDto= chapterService.getByIdChapter(id);
        return ResponseEntity.ok(chapterDto);
    }
    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get chapters by course ID", description = "Retrieves a list of all chapters belonging to a specific course")
    public ResponseEntity<List<ChapterDto>> getChapterByCourseId(@PathVariable Long courseId){
        List<ChapterDto> chapterDtos=chapterService.getChapterByCourseId(courseId);
        return ResponseEntity.ok(chapterDtos);
    }
    @PostMapping("/create")
    @Operation(summary = "Create a new chapter", description = "Adds a new chapter record to the database")
    public ResponseEntity<ChapterDto> createChapter(@RequestBody ChapterDto chapterDto){
        ChapterDto createChapter= chapterService.createChapter(chapterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createChapter);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update chapter", description = "Updates an existing chapter by its ID")
    public ResponseEntity<ChapterDto> updateChapter(@PathVariable Long id,
                                                    @RequestBody ChapterDto chapterDto){
        ChapterDto updChapter= chapterService.updateChapter(id,chapterDto);
        return ResponseEntity.ok(updChapter);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete chapter", description = "Removes a chapter from the database by its ID")
    public ResponseEntity<Boolean> deleteChapter(@PathVariable Long id){
        Boolean chapterDto= chapterService.deleteChapter(id);
        return ResponseEntity.ok(chapterDto);
    }

}
