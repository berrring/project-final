package org.example.projectfinal.employess.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record EmployeeDTO(
        Long id,
        @NotBlank(message = "Name is required") String name,
        @Email @NotBlank(message = "Email is required") String email,
        @Past(message = "Birth date must be in the past") LocalDate birthDate,
        @Min(value = 5001, message = "Salary must be greater than 5000") Integer salary,
        Integer age,
        Long departmentId
) {}