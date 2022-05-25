package training.skills;

import org.junit.jupiter.api.Test;
import training.skills.domain.model.entity.Employee;
import training.skills.domain.model.events.EmployeeHasCreatedEvent;
import training.skills.domain.model.events.SkillHasAcquiredEvent;
import training.skills.domain.model.events.SkillUpdatedEvent;
import training.skills.domain.model.value.AcquireSkillsCommand;
import training.skills.domain.model.value.CreateEmployeeCommand;
import training.skills.domain.model.value.SkillLevel;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeTest {

    @Test
    void createEmployee() {
        var employee = Employee.createEmployee(new CreateEmployeeCommand("John Doe"));
        assertEquals("John Doe", employee.getName());
        assertThat(employee.skillLevels()).isEmpty();
        assertThat(employee.eventsWithoutId()).containsExactly(new EmployeeHasCreatedEvent(0L, "John Doe"));
    }

    @Test
    void acquireSkills() {
        var employee = Employee.createEmployee(new CreateEmployeeCommand("John Doe"));
        employee.acquireSkills(new AcquireSkillsCommand(List.of(new SkillLevel(1L, 3), new SkillLevel(2L, 4))));

        assertThat(employee.skillLevels()).
                containsExactly(new SkillLevel(1L, 3), new SkillLevel(2L, 4));
        assertThat(employee.eventsWithoutId()).contains(new SkillHasAcquiredEvent(0L, 1L, 3),
                new SkillHasAcquiredEvent(0L, 2L, 4));
    }

    @Test
    void upgradeSkill() {
        // Given
        var employee = Employee.createEmployee(new CreateEmployeeCommand("John Doe"));
        employee.acquireSkills(new AcquireSkillsCommand(List.of(new SkillLevel(1L, 3))));

        // When
        employee.acquireSkills(new AcquireSkillsCommand(List.of(new SkillLevel(1L, 4))));

        assertThat(employee.skillLevels()).
                containsExactly(new SkillLevel(1L, 4));
        assertThat(employee.eventsWithoutId()).contains(new SkillUpdatedEvent(0L, 1L, 3, 4));
    }
}
