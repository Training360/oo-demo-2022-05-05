package training.skills.domain.model.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class AcquireSkillsCommand {

    List<SkillLevel> skills;

    @JsonCreator
    public AcquireSkillsCommand(@JsonProperty("skills") List<SkillLevel> skills) {
        this.skills = skills;
    }
}
