package training.skills.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import training.skills.domain.model.entity.Employee;
import training.skills.domain.services.EmployeeDomainRepository;
import training.skills.query.EmployeeQueryRepository;
import training.skills.query.model.AcquiredSkill;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeDomainRepository, EmployeeQueryRepository {

    @Query("select distinct e from Employee e left join fetch e.acquiredSkills where e.id = :employeeId")
    Optional<Employee> findEmployeeWithSkills(@Param("employeeId") long employeeId);

    @Query("select new training.skills.query.model.AcquiredSkill(s.skillLevel.skillId, s.skillLevel.level) from AcquiredSkill s where s.employee.id = :employeeId")
    List<AcquiredSkill> listAcquiredSkills(@Param("employeeId") long employeeId);
}
