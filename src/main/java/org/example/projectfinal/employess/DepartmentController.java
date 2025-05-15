package org.example.projectfinal.employess;

import jakarta.validation.Valid;
import org.example.projectfinal.employess.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    private final DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public Page<DepartmentDTO> getAllDepartments(@PageableDefault(size = 20) Pageable pageable) {
        return service.getAllDepartments(pageable);
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO dto) {
        DepartmentDTO created = service.createDepartment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}