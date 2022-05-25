package training.skills.domain.services;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import training.skills.domain.model.events.DomainEvent;

@Service
@AllArgsConstructor
public class EventBusService {

    EventBusGateway eventBusGateway;

    @EventListener
    public void handleEvent(DomainEvent domainEvent) {
        eventBusGateway.sendEvent(domainEvent);
    }
}
