package com.bitlab.internship.controller;

import com.bitlab.internship.dto.CourseDto;
import com.bitlab.internship.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
@Tag(name = "Course Controller", description = "Courses management APIs")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get all courses", description = "Retrieves a list of all available courses")
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        List<CourseDto> courses=courseService.getAllCourse();
        return ResponseEntity.ok(courses);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get course by ID", description = "Retrieves a specific course by its unique identifier")
    public ResponseEntity<CourseDto> getByIdCourse(@PathVariable Long id){
        CourseDto courseDto=courseService.getByIdCourse(id);
        return ResponseEntity.ok(courseDto);
    }
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Create a new course", description = "Adds a new course record to the database")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto){
        CourseDto createCourse= courseService.createCourse(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCourse);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Update course", description = "Updates an existing course by its ID")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id,
                                                  @Valid @RequestBody CourseDto courseDto){
        CourseDto updCourse=courseService.updateCourse(id,courseDto);
        return ResponseEntity.ok(updCourse);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @Operation(summary = "Delete course", description = "Removes a course from the database by its ID")
    public ResponseEntity<Boolean> deleteCourse(@PathVariable Long id){
        boolean deleteCourse= courseService.deleteCourse(id);
        return ResponseEntity.ok(deleteCourse);
    }
}
