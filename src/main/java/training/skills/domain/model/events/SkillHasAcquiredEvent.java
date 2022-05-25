package training.skills.domain.model.events;

import lombok.Value;

@Value
public class SkillHasAcquiredEvent implements DomainEvent {

    long employeeId;

    long skillId;

    int level;
}
