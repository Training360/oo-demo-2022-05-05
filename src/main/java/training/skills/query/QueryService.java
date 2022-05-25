package training.skills.query;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training.skills.query.model.AcquiredSkill;

import java.util.List;

@Service
@AllArgsConstructor
public class QueryService {

    private EmployeeQueryRepository employeeRepository;

    public List<AcquiredSkill> listAcquiredSkills(long employeeId) {
        return employeeRepository.listAcquiredSkills(employeeId);
    }
}
