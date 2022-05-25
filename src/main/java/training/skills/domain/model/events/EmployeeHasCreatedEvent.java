package training.skills.domain.model.events;

import lombok.Value;

@Value
public class EmployeeHasCreatedEvent implements DomainEvent {

    Long id;

    String name;

    public EmployeeHasCreatedEvent(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
