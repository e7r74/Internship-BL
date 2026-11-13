package com.bitlab.internship.controller;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chapters")
public class ChapterController {
    private final ChapterService chapterService;

    @GetMapping
    public ResponseEntity<List<ChapterDto>> getAllChapters(){
        List<ChapterDto> chapterDtos= chapterService.getAllChapters();
        return ResponseEntity.ok(chapterDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ChapterDto> getByIdChapter(@PathVariable Long id){
        ChapterDto chapterDto= chapterService.getByIdChapter(id);
        return ResponseEntity.ok(chapterDto);
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<ChapterDto>> getChapterByCourseId(@PathVariable Long courseId){
        List<ChapterDto> chapterDtos=chapterService.getChapterByCourseId(courseId);
        return ResponseEntity.ok(chapterDtos);
    }
    @PostMapping("/create")
    public ResponseEntity<ChapterDto> createChapter(@RequestBody ChapterDto chapterDto){
        ChapterDto createChapter= chapterService.createChapter(chapterDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createChapter);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChapterDto> updateChapter(@PathVariable Long id,
                                                    @RequestBody ChapterDto chapterDto){
        ChapterDto updChapter= chapterService.updateChapter(id,chapterDto);
        return ResponseEntity.ok(updChapter);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteChapter(@PathVariable Long id){
        Boolean chapterDto= chapterService.deleteChapter(id);
        return ResponseEntity.ok(chapterDto);
    }

}
