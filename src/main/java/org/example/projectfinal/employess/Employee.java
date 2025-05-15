package org.example.projectfinal.employess;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private Long ID;

    private String name;
    private String email;
    private LocalDate birthDate;
    private Integer salary;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Employee() {}

    public Employee(Long ID, String name, String email, LocalDate birthDate, Integer salary) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.salary = salary;
    }

    public Long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getSalary() {
        return salary;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Transient
    public Integer getAge() {
        if (birthDate == null) return null;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    public Department getDepartment() {
        return department; }
    public void setDepartment(Department department) {
        this.department = department; }
}
