package training.skills.domain.model.value;

import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class SkillLevel {

    private Long skillId;

    private int level;

    private SkillLevel() {
        // Thanks to Hibernate
        skillId = null;
        level = 0;
    }

    public SkillLevel(Long skillId, int level) {
        this.skillId = skillId;
        this.level = level;
    }

    public boolean hasToUpgrade(SkillLevel another) {
        return another.level > level;
    }

    public boolean sameSkill(SkillLevel another) {
        return skillId == another.skillId;
    }
}
