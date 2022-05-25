package training.skills.domain.services;

import org.springframework.data.repository.query.Param;
import training.skills.domain.model.entity.Employee;

import java.util.Optional;

public interface EmployeeDomainRepository {

    Employee save(Employee employee);

    Optional<Employee> findEmployeeWithSkills(@Param("employeeId") long employeeId);
}
