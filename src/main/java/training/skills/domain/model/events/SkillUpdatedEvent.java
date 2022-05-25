package training.skills.domain.model.events;

import lombok.Value;

@Value
public class SkillUpdatedEvent implements DomainEvent {

    private long employeeId;

    private long skillId;

    private int oldLevel;

    private int newLevel;
}
