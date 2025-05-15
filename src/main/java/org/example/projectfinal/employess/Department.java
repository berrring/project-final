package org.example.projectfinal.employess;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_sequence")
    @SequenceGenerator(name = "department_sequence", sequenceName = "department_sequence", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Department name is required")
    private String name;

    private String description;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

    public Department() {}

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
}