package com.afniko.core.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.afniko.core.model.NodeDesc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class NodeDescRepositoryTest {

    @Autowired
    private NodeDescRepository repository;

    @DisplayName("given object to save when save object using MongoDB template then object is saved")
    void givenOwner_whenFindFirstByOwner_thenFindAccount() {
        String id = "1";
        String name = "name1";
        String description = "description1";

        repository.save(new NodeDesc(id, name, description));
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
