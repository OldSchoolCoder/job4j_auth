package ru.job4j.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Employee;
import ru.job4j.domain.Person;
import ru.job4j.repository.EmployeeRepository;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return StreamSupport.stream(
                employeeRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employee.setCreated(Calendar.getInstance());
        var savedEmployee = employeeRepository.save(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Employee employee = new Employee();
        employee.setId(id);
        employeeRepository.delete(employee);
        return ResponseEntity.ok().build();
    }
}
