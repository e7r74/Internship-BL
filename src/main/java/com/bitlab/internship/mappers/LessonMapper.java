package com.bitlab.internship.mappers;

import com.bitlab.internship.dto.LessonDto;
import com.bitlab.internship.model.Chapter;
import com.bitlab.internship.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    @Mapping(source = "name", target = "lessonName")
    @Mapping(source = "content", target = "lessonContent")
    @Mapping(source = "order", target = "lessonOrder")
    @Mapping(source = "chapter.id",target = "chapterId")
    LessonDto toDto(Lesson lesson);

    @Mapping(source = "lessonName", target = "name")
    @Mapping(source = "lessonContent",target = "content")
    @Mapping(source = "lessonOrder", target = "order")
    @Mapping(source = "chapterId", target = "chapter.id")
    Lesson toEntity(LessonDto lessonDto);

    List<LessonDto> toDtoList(List<Lesson> lessons);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "chapter", ignore = true)
    @Mapping(target = "createdTime",ignore = true)
    @Mapping(target = "updateTime",ignore = true)
    @Mapping(source = "lessonName", target = "name")
    @Mapping(source = "lessonContent",target = "content")
    @Mapping(source = "lessonOrder", target = "order")
    void updateEntityFromDto(LessonDto lessonDto, @MappingTarget Lesson lesson);
}

