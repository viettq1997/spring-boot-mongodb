package com.viettq.springbootwithmongodb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class SpringbootwithmongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootwithmongodbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(
			StudentRepository studentRepository, MongoTemplate mongoTemplate) {
		return args -> {
			Address address = new Address(
					"Viet Nam",
					"Nghe An"
			);


			String email = "vietpro23w@gmail.com";
			Student student = new Student(
					"viettq",
					"tran quoc",
					email,
					Gender.FEMALE,
					address,
					List.of("MATH", "IT", "ENGLISH"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

//			usingMongoTemplate(studentRepository, mongoTemplate, email, student);

			studentRepository.findStudentByEmail(email)
					.ifPresentOrElse(s -> {
						System.out.println(student + "already exist!");
					}, () -> {
						System.out.println("insert student " + student);
						studentRepository.insert(student);
					});
		};
	}

	private void usingMongoTemplate(StudentRepository studentRepository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if (students.size() > 1) {
			throw new IllegalStateException("found many students with email " + email);
		}

		if (students.isEmpty()) {
			System.out.println("insert student " + student);
			studentRepository.insert(student);
		} else {
			System.out.println(student + "already exist!");
		}
	}
}
