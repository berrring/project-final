package org.example.projectfinal.employess;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class EmployeeConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            EmployeeRepository employeeRepository
    ){
        return (args )->{
            if(employeeRepository.count() == 0){
                var employeeList = List.of(
                        new Employee(
                                null,
                                "Alex",
                                "Alea@gmail.com",
                                LocalDate.of(2000,1,10),
                                10000
                        ),
                        new Employee(
                                null,
                                "Sar",
                                "Sar@gmail.com",
                                LocalDate.of(2000,1,11),

                                11111
                        )
                );
                employeeRepository.saveAll(employeeList);

            };

        };


    }
}
