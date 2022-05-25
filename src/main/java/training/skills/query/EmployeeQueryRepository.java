package training.skills.query;

import org.springframework.data.repository.query.Param;
import training.skills.query.model.AcquiredSkill;

import java.util.List;

public interface EmployeeQueryRepository {

    List<AcquiredSkill> listAcquiredSkills(@Param("employeeId") long employeeId);
}
