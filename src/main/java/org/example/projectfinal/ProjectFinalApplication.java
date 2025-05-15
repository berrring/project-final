package org.example.projectfinal;

import org.example.projectfinal.employess.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;


@SpringBootApplication //помечает, что данный класс главный в нашем SpringBoot приложении
public class ProjectFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectFinalApplication.class, args);

    }


}
