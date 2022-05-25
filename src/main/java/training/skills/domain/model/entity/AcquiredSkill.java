package training.skills.domain.model.entity;

import lombok.Getter;
import training.skills.domain.model.events.DomainEventStore;
import training.skills.domain.model.events.SkillHasAcquiredEvent;
import training.skills.domain.model.events.SkillUpdatedEvent;
import training.skills.domain.model.value.SkillLevel;

import javax.persistence.*;

@Entity
public class AcquiredSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    @Getter
    private SkillLevel skillLevel;

    @ManyToOne
    private Employee employee;

    private AcquiredSkill() {
    }

    public static AcquiredSkill create(SkillLevel skillLevel, DomainEventStore eventStore, Employee employee) {
        var acquiredSkill = new AcquiredSkill();
        acquiredSkill.skillLevel = skillLevel;
        acquiredSkill.employee = employee;
        eventStore.registerEvent(id -> new SkillHasAcquiredEvent(id, skillLevel.getSkillId(), skillLevel.getLevel()));
        return acquiredSkill;
    }

    public void upgradeIf(SkillLevel newSkillLevel, DomainEventStore eventStore) {
        if (this.skillLevel.hasToUpgrade(newSkillLevel)) {
            var oldSkillLevel = skillLevel;
            skillLevel = newSkillLevel;
            eventStore.registerEvent(id -> new SkillUpdatedEvent(id, skillLevel.getSkillId(), oldSkillLevel.getLevel(), newSkillLevel.getLevel()));
        }
    }

    public boolean sameSkill(SkillLevel another) {
        return this.skillLevel.sameSkill(another);
    }

}
