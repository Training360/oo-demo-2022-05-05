package training.skills.application;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training.skills.domain.model.value.AcquireSkillsCommand;
import training.skills.domain.model.value.CreateEmployeeCommand;
import training.skills.domain.services.CreateEmployeeResponse;
import training.skills.domain.services.EmployeeService;
import training.skills.query.QueryService;
import training.skills.query.model.AcquiredSkill;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeService employeeService;

    private QueryService queryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateEmployeeResponse createEmployee(@RequestBody CreateEmployeeCommand command) {
        return employeeService.createEmployee(command);
    }

    @PostMapping("{id}/acquired-skills")
    public void acquireSkills(@PathVariable("id") long employeeId, @RequestBody AcquireSkillsCommand command) {
        employeeService.acquireSkills(employeeId, command);
    }

    @GetMapping("{id}/acquired-skills")
    public List<AcquiredSkill> listAcquiredSkills(@PathVariable("id") long employeeId) {
        return queryService.listAcquiredSkills(employeeId);
    }

}
