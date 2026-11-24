package com.bitlab.internship.mappers;

import com.bitlab.internship.dto.ChapterDto;
import com.bitlab.internship.model.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    @Mapping(source = "name", target = "chapterName")
    @Mapping(source = "order", target = "chapterOrder")
    @Mapping(source = "course.id", target = "courseId")
    ChapterDto toDto(Chapter chapter);

    @Mapping(source = "chapterName", target = "name")
    @Mapping(source = "chapterOrder", target = "order")
    @Mapping(source = "courseId",target = "course.id")
    Chapter toEntity(ChapterDto chapterDto);

    List<ChapterDto> toDtoList(List<Chapter> chapters);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(source = "chapterName", target = "name")
    @Mapping(source = "chapterOrder", target = "order")
    void updateEntityFromDto(ChapterDto chapterDto, @MappingTarget Chapter chapter);
}
