package training.skills.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import training.skills.domain.model.events.DomainEvent;
import training.skills.domain.services.EventBusGateway;

@Service
@Slf4j
public class ConsoleEventBusGateway implements EventBusGateway {

    @Override
    public void sendEvent(DomainEvent domainEvent) {
        log.debug(domainEvent.toString());
    }
}
