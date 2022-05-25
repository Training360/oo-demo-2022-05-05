package training.skills.domain.model.entity;

import lombok.Getter;
import training.skills.domain.model.events.DomainEventStore;
import training.skills.domain.model.events.DomainEvent;
import training.skills.domain.model.events.EmployeeHasCreatedEvent;
import training.skills.domain.model.value.AcquireSkillsCommand;
import training.skills.domain.model.value.CreateEmployeeCommand;
import training.skills.domain.model.value.SkillLevel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Long id;

    @Getter
    private String name;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<AcquiredSkill> acquiredSkills = new ArrayList<>();

    @Transient
    private DomainEventStore eventStore = new DomainEventStore();

    private Employee() {
    }

    public static Employee createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee();
        employee.name = command.getName();
        employee.registerEmployeeHasCreatedEvent();
        return employee;
    }

    public List<DomainEvent> events() {
        if (id == null) {
            throw new IllegalStateException("Not saved. Are you testing? Try eventsWithoutId() method!");
        }
        return eventStore.events(id);
    }

    public List<DomainEvent> eventsWithoutId() {
        return eventStore.events(0);
    }

    private void registerEmployeeHasCreatedEvent() {
        eventStore.registerEvent(id -> new EmployeeHasCreatedEvent(id, name));
    }

    public void acquireSkills(AcquireSkillsCommand command) {
        for (var skillToAcquire: command.getSkills()) {
            var foundAcquiredSkill = acquiredSkills.stream().filter(s -> s.sameSkill(skillToAcquire)).findFirst();
            if (foundAcquiredSkill.isPresent()) {
                foundAcquiredSkill.get().upgradeIf(skillToAcquire, eventStore);
            }
            else {
                var acquiredSkill = AcquiredSkill.create(skillToAcquire, eventStore, this);
                acquiredSkills.add(acquiredSkill);
            }
        }
    }

    public List<SkillLevel> skillLevels() {
        return acquiredSkills.stream().map(AcquiredSkill::getSkillLevel).toList();
    }
}
