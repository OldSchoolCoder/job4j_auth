package ru.job4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;
import ru.job4j.repository.EmployeeRepository;
import ru.job4j.repository.PersonRepository;

@SpringBootApplication
public class Job4jAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(Job4jAuthApplication.class, args);
	}
}
