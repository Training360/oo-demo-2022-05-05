package training.skills.domain.model.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DomainEventStore {

    private List<Function<Long, DomainEvent>> events = new ArrayList<>();

    public void registerEvent(Function<Long, DomainEvent> event) {
        events.add(event);
    }

    public List<DomainEvent> events(long id) {
        // unmodifiableList
        return events.stream().map(event -> event.apply(id)).toList();

    }

}
