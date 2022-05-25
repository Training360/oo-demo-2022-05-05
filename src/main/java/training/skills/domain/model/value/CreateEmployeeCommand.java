package training.skills.domain.model.value;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class CreateEmployeeCommand {

    @NotBlank
    String name;

    @JsonCreator
    public CreateEmployeeCommand(@JsonProperty("name") String name) {
        this.name = name;
    }
}
