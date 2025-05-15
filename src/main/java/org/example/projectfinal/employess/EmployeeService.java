package org.example.projectfinal.employess;


import org.example.projectfinal.employess.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository; // Внедрен новый репозиторий

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(this::toDTO);
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));
        Employee employee = toEntity(employeeDTO);
        employee.setDepartment(department);
        validateEmployee(employee, true);
        Employee saved = employeeRepository.save(employee);
        return toDTO(saved);
    }

    public void deleteEmployee(Long id) {
        if (employeeRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found: id=" + id);
        }
        employeeRepository.deleteById(id);
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found: id=" + id);
        }

        Employee existing = optionalEmployee.get();
        Employee updated = toEntity(employeeDTO);
        validateEmployee(updated, false);

        if (updated.getName() != null) {
            existing.setName(updated.getName());
        }
        if (updated.getEmail() != null && !updated.getEmail().equals(existing.getEmail())) {
            if (employeeRepository.findByEmail(updated.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already exists: " + updated.getEmail());
            }
            existing.setEmail(updated.getEmail());
        }
        if (updated.getBirthDate() != null) {
            existing.setBirthDate(updated.getBirthDate());
        }
        if (updated.getSalary() != null) {
            existing.setSalary(updated.getSalary());
        }
        if (employeeDTO.departmentId() != null) {
            Department department = departmentRepository.findById(employeeDTO.departmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Department not found"));
            existing.setDepartment(department);
        }

        Employee saved = employeeRepository.save(existing);
        return toDTO(saved);
    }

    private void validateEmployee(Employee employee, boolean isNew) {
        if (isNew && (employee.getName() == null || employee.getName().isBlank())) {
            throw new IllegalArgumentException("Name is required");
        }
        if (employee.getEmail() == null || employee.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (employee.getBirthDate() != null && employee.getBirthDate().isAfter(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
        if (employee.getSalary() == null || employee.getSalary() <= 5000) {
            throw new IllegalArgumentException("Salary must be greater than 5000");
        }
        if (isNew && employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + employee.getEmail());
        }
    }

    private EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getID(),
                employee.getName(),
                employee.getEmail(),
                employee.getBirthDate(),
                employee.getSalary(),
                employee.getAge(),
                employee.getDepartment() != null ? employee.getDepartment().getId() : null
        );
    }

    private Employee toEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.name());
        employee.setEmail(employeeDTO.email());
        employee.setBirthDate(employeeDTO.birthDate());
        employee.setSalary(employeeDTO.salary());
        return employee;
    }
}