package com.viettq.springbootwithmongodb;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository
        extends MongoRepository<Student, String> {
    Optional<List<Student>> findStudentsByEmail(String email, Pageable pageable);
}
