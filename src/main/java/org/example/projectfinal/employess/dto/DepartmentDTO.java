package org.example.projectfinal.employess.dto;


import jakarta.validation.constraints.NotBlank;

public record DepartmentDTO(
        Long id,
        @NotBlank(message = "Department name is required") String name,
        String description
) {}