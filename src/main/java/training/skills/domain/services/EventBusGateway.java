package training.skills.domain.services;

import training.skills.domain.model.events.DomainEvent;

public interface EventBusGateway {

    void sendEvent(DomainEvent domainEvent);
}
