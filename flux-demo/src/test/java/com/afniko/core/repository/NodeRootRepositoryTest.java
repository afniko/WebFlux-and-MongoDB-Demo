package com.afniko.core.repository;

import com.afniko.core.model.NodeRoot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
class NodeRootRepositoryTest {

    @Autowired
    private NodeRootRepository repository;

    @Test
    void givenOwner_whenFindFirstByOwner_thenFindAccount() {
        String id = "1";
        String name = "name1";

        repository.save(new NodeRoot(id, name)).block();
        Mono<NodeRoot> accountMono = repository.findById(id);

        StepVerifier.create(accountMono)
                .assertNext(node -> {
                    assertEquals(name, node.getName());
                    assertNotNull(node.getId());
                })
                .expectComplete()
                .verify();
    }
}
