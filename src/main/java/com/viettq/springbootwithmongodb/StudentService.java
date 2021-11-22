package com.viettq.springbootwithmongodb;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final MongoTemplate mongoTemplate;

    public List<Student> fetchStudents(int size) {
        Pageable pageable = PageRequest.of(0, size);
        long start = System.currentTimeMillis();

        // use jpa
//        Optional<List<Student>> studentsTemp = studentRepository.findStudentsByEmail("viettq23@gmail.com", pageable);

        // use mongoTemplate
        List<Student> students = usingMongoTemplate(pageable);
        long end = System.currentTimeMillis();
        // jpa
//        Optional<Integer> count = studentsTemp.map(List::size);

        // use mongoTemplate
        int count = students.size();
        logger.info("get " + count + " took " + (end - start));

        // jpa
//        return studentsTemp.orElse(null);

        // use mongoTemplate
        return students;
    }

    private List<Student> usingMongoTemplate(Pageable pageable) {
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("email").is("viettq23@gmail.com"));

        return mongoTemplate.find(query, Student.class);
    }

    public Student saveStudent(Student student) {
        if (student.getId() != null) {
            Optional<Student> studentTemp = studentRepository.findById(student.getId());
            studentTemp.ifPresent(s -> {
                studentRepository.save(student);
            });
        } else {
            studentRepository.save(student);
        }
        return student;
    }
}
