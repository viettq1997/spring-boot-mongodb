package com.viettq.springbootwithmongodb;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
@AllArgsConstructor
public class StudentController {

    private StudentService studentService;

    @GetMapping("/{size}")
    public List<Student> fetchStudents(@PathVariable(name = "size") int size) {
        return studentService.fetchStudents(size);
    }

    @PostMapping
    public Student saveStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }
}
