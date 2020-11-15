package com.afniko.core.repository;

import com.afniko.core.model.NodeDesc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
class NodeDescRepositoryTest {

    @Autowired
    private NodeDescRepository repository;

    @Test
    void givenOwner_whenFindFirstByOwner_thenFindAccount() {
        String id = "1";
        String name = "name1";
        String description = "description1";

        repository.save(new NodeDesc(id, name, description)).block();
        Mono<NodeDesc> accountMono = repository.findById(id);

        StepVerifier.create(accountMono)
                .assertNext(node -> {
                    assertEquals(name, node.getName());
                    assertEquals(description, node.getDescription());
                    assertNotNull(node.getId());
                })
                .expectComplete()
                .verify();
    }
}
