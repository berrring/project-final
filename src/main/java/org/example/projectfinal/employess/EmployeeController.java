package org.example.projectfinal.employess;

import jakarta.validation.Valid;
import org.example.projectfinal.employess.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public Page<EmployeeDTO> getAllEmployees(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return employeeService.getAllEmployees(pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO created = employeeService.createEmployee(employeeDTO, employeeDTO.departmentId());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable("id") Long id,
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO updated = employeeService.updateEmployee(id, employeeDTO);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}