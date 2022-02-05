package ru.job4j.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String secondName;
    private Integer inn;
    private Calendar created;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Person> personList = new ArrayList<>();

    public Employee() {
    }

    public Employee(String firstName) {
        this.firstName = firstName;
    }

    public void addPerson(Person person) {
        personList.add(person);
    }
}
