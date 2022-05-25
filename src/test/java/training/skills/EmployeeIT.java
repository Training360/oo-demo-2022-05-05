package training.skills;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import training.skills.domain.model.value.AcquireSkillsCommand;
import training.skills.domain.model.value.CreateEmployeeCommand;
import training.skills.domain.model.value.SkillLevel;
import training.skills.domain.services.CreateEmployeeResponse;
import training.skills.query.model.AcquiredSkill;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from employee")
class EmployeeIT {

    @Autowired
    WebTestClient webClient;

    @Test
    void acquireSkills() {
        var id = webClient
                .post()
                .uri("/api/employees")
                .bodyValue(new CreateEmployeeCommand("John Doe"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CreateEmployeeResponse.class).returnResult().getResponseBody().getId();

        webClient
                .post()
                .uri("/api/employees/{id}/acquired-skills", id)
                .bodyValue(new AcquireSkillsCommand(List.of(new SkillLevel(1L, 3))))
                .exchange()
                .expectStatus().isOk();

        webClient
                .get()
                .uri("/api/employees/{id}/acquired-skills", id)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AcquiredSkill.class).hasSize(1).contains(new AcquiredSkill(1L, 3));
    }

}
