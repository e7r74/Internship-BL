package com.bitlab.internship.mappers;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.model.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    @Mapping(source = "name", target = "chapterName")
    @Mapping(source = "order", target = "chapterOrder")
    @Mapping(source = "chapter.id", target = "chapterId")
    ChapterDto toDto(Chapter chapter);

    @Mapping(source = "chapterName", target = "name")
    @Mapping(source = "chapterOrder", target = "order")
    @Mapping(source = "chapterId",target = "chapter.id")
    Chapter toEntity(ChapterDto chapterDto);

    List<ChapterDto> toDtoList(List<Chapter> chapters);
}
