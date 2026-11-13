package com.bitlab.internship.service.impl;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.mappers.ChapterMapper;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.model.Course;
import com.bitlab.internship.repository.ChapterRepository;
import com.bitlab.internship.repository.CourseRepository;
import com.bitlab.internship.service.ChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final ChapterRepository chapterRepository;
    private final  ChapterMapper chapterMapper;
    private final CourseRepository courseRepository;
    @Override
    public List<ChapterDto> getAllChapters() {
        log.info("Fetching all courses");
        List<Chapter> chapters=chapterRepository.findAll();
        return chapterMapper.toDtoList(chapters);
    }

    @Override
    public ChapterDto getByIdChapter(Long id) {
        log.info("Fetching chapter by id: {}",id);
        Chapter chapter=checkChapter(id);
        return chapterMapper.toDto(chapter);
    }

    @Override
    public List<ChapterDto> getChapterByCourseId(Long courseId) {
       log.info("Fetching chapter by courseId: {}", courseId);
       List<Chapter> chapters=chapterRepository.findAllByCourseId(courseId);
       return chapterMapper.toDtoList(chapters);
    }

    @Override
    public ChapterDto createChapter(ChapterDto chapterDto) {
        log.info("Creating new chapter");
       Chapter chapter=chapterMapper.toEntity(chapterDto);
       log.debug("Saving chapter object: {}",chapter);
       return chapterMapper.toDto( chapterRepository.save(chapter));
    }

    @Override
    public ChapterDto updateChapter(Long id, ChapterDto chapterDto) {
        log.info("Update chapter with id: {}",id);
        Course course=null;
        if (chapterDto.getCourseId()!=null){
            course= courseRepository.findById(chapterDto.getCourseId())
                                    .orElseThrow(()->{
                                        log.error("Cannot update chapter: Course not found with id: {}",chapterDto.getCourseId());
                                      return new IllegalArgumentException("Cannot update chapter: Course not found with id: "+chapterDto.getCourseId());
                                    });
        }
        Chapter updChapter=checkChapter(id);
        updChapter.setName(chapterDto.getChapterName());
        updChapter.setDescription(chapterDto.getDescription());
        updChapter.setOrder(chapterDto.getChapterOrder());
        updChapter.setCourse(course);
        log.debug("Updating chapter object: {}",updChapter);
        return chapterMapper.toDto(chapterRepository.save(updChapter));
    }

    @Override
    public boolean deleteChapter(Long id) {
        log.info("Delete chapter with id: {}",id);
        Chapter delChapter=checkChapter(id);
        chapterRepository.delete(delChapter);
        return true;
    }

    private Chapter checkChapter(Long id){
       return chapterRepository.findById(id)
                                .orElseThrow(()->{
                                    log.error("ChaChapter not found with id: {}",id);
                                    return new IllegalArgumentException("Chapter not found with id: "+id);
                                });


    }
}
