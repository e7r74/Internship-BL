package com.bitlab.internship.service;


import com.bitlab.internship.dto.ChapterDto;

import java.util.List;

public interface ChapterService {
    List<ChapterDto> getAllChapters();
    ChapterDto getByIdChapter(Long id);
    List<ChapterDto> getChapterByCourseId(Long courseId);
    ChapterDto updateChapter(Long id, ChapterDto chapterDto);
    ChapterDto createChapter(ChapterDto chapterDto);
    boolean deleteChapter(Long id);
}
