package com.bitlab.internship.service.impl;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.mappers.ChapterMapper;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.model.Course;
import com.bitlab.internship.repository.ChapterRepository;
import com.bitlab.internship.repository.CourseRepository;
import com.bitlab.internship.service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final  ChapterMapper chapterMapper;
    private final CourseRepository courseRepository;
    @Override
    public List<ChapterDto> getAllChapters() {
        List<Chapter> chapters=chapterRepository.findAll();
        return chapterMapper.toDtoList(chapters);
    }

    @Override
    public ChapterDto getByIdChapter(Long id) {
        Chapter chapter=checkChapter(id);
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
    public ChapterDto updateChapter(Long id, ChapterDto chapterDto) {
        Course course=null;
        if (chapterDto.getCourseId()!=null){
            course= courseRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Cannot update chapter: Course not found with id: "+chapterDto.getCourseId()));
        }
        Chapter updChapter=checkChapter(id);
        updChapter.setName(chapterDto.getChapterName());
        updChapter.setDescription(chapterDto.getDescription());
        updChapter.setOrder(chapterDto.getChapterOrder());
        updChapter.setCourse(course);
        return chapterMapper.toDto(chapterRepository.save(updChapter));
    }

    @Override
    public boolean deleteChapter(Long id) {
        Chapter delChapter=checkChapter(id);
        chapterRepository.delete(delChapter);
        return true;
    }

    private Chapter checkChapter(Long id){
        Chapter checkChapter=chapterRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Chapter not found with id: "+id));
        return checkChapter;
    }
}
