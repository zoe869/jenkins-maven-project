package edu.tus.jpa.controllers;

import edu.tus.jpa.dto.Course;
import edu.tus.jpa.dto.Module;
import edu.tus.jpa.exceptions.ResourceNotFoundException;
import edu.tus.jpa.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class JpaDemoController
{
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable(value = "id") Long courseId) throws ResourceNotFoundException {
        Optional<Course> course = courseRepository.findById(courseId);
        if(course.isPresent())
            return ResponseEntity.ok().body(course.get());
        else
            throw new ResourceNotFoundException("Course not found :: " + courseId);
    }

    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @GetMapping("/dummy")
    public Course createDummy() {
        Module module1 = new Module("Microservice Architecture", 10);
        Module module2 = new Module("Databases", 5);
        List<Module> courseModules = new ArrayList<>();
        courseModules.add(module1);
        courseModules.add(module2);
        Course course = new Course("Software Masters", "Engineering");
        course.setModules(courseModules);
        return courseRepository.save(course);
    }
}
