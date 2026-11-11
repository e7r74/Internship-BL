package com.bitlab.internship.service.impl;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.mappers.ChapterMapper;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.repository.ChapterRepository;
import com.bitlab.internship.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final  ChapterMapper chapterMapper;
    @Override
    public List<ChapterDto> getAllChapters() {
        List<Chapter> chapters=chapterRepository.findAll();
        return chapterMapper.toDtoList(chapters);
    }

    @Override
    public ChapterDto getByIdChapter(Long id) {
        Chapter chapter= chapterRepository.findById(id).orElseThrow(()->new RuntimeException("Chapter not found with id: "+id));
        return chapterMapper.toDto(chapter);
    }

    @Override
    public List<ChapterDto> getChapterByCourseId(Long courseId) {
       List<Chapter> chapters=chapterRepository.findAllByCourseId(courseId);
       return chapterMapper.toDtoList(chapters);
    }

    @Override
    public ChapterDto createChapter(ChapterDto chapterDto) {
       Chapter chapter=chapterMapper.toEntity(chapterDto);
       return chapterMapper.toDto( chapterRepository.save(chapter));
    }

    @Override
    public boolean deleteChapter(Long id) {
        Chapter delChapter=chapterRepository.findById(id).orElseThrow(()->new RuntimeException("Chapter not found with id: "+id));
        chapterRepository.delete(delChapter);
        return true;
    }
}
