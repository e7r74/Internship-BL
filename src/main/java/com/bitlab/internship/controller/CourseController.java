package com.bitlab.internship.controller;

import com.bitlab.internship.dto.CourseDto;
import com.bitlab.internship.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        List<CourseDto> courses=courseService.getAllCourse();
        return ResponseEntity.ok(courses);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getByIdCourse(@PathVariable Long id){
        CourseDto courseDto=courseService.getByIdCourse(id);
        return ResponseEntity.ok(courseDto);
    }
    @PostMapping("/create")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseDto courseDto){
        CourseDto createCourse= courseService.createCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCourse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCourse(@PathVariable Long id){
        boolean deleteCourse= courseService.deleteCourse(id);
        return ResponseEntity.ok(deleteCourse);
    }
}
