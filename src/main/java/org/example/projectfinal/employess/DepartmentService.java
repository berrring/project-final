package org.example.projectfinal.employess;



import org.example.projectfinal.employess.dto.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    private final DepartmentRepository repository;

    @Autowired
    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        Department department = new Department(dto.name(), dto.description());
        Department saved = repository.save(department);
        return new DepartmentDTO(saved.getId(), saved.getName(), saved.getDescription());
    }

    public Page<DepartmentDTO> getAllDepartments(Pageable pageable) {
        return repository.findAll(pageable)
                .map(d -> new DepartmentDTO(d.getId(), d.getName(), d.getDescription()));
    }
}