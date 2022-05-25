package training.skills.domain.services;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class CreateEmployeeResponse {

    long id;

    @JsonCreator
    public CreateEmployeeResponse(@JsonProperty("id") long id) {
        this.id = id;
    }
}
