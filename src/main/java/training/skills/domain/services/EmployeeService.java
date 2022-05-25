package training.skills.domain.services;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.skills.domain.model.entity.Employee;
import training.skills.domain.model.events.DomainEvent;
import training.skills.domain.model.value.AcquireSkillsCommand;
import training.skills.domain.model.value.CreateEmployeeCommand;

@Service
@AllArgsConstructor
public class EmployeeService {

    private EmployeeDomainRepository employeeRepository;

    private ApplicationEventPublisher publisher;

    @Transactional
    public CreateEmployeeResponse createEmployee(CreateEmployeeCommand command) {
        var employee = Employee.createEmployee(command);
        employeeRepository.save(employee);
        employee.events().forEach(this::publishEvent);
        return new CreateEmployeeResponse(employee.getId());
    }

    @Transactional
    public void acquireSkills(Long employeeId, AcquireSkillsCommand command) {
        var employee = employeeRepository.findEmployeeWithSkills(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Not found with id: %d", employeeId)));
        employee.acquireSkills(command);
        employee.events().forEach(this::publishEvent);
    }

    private void publishEvent(DomainEvent e) {
        publisher.publishEvent(e);
    }
}
