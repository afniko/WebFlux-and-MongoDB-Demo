package com.afniko.core.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.afniko.core.model.NodeRoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
class NodeRootRepositoryTest {

    @Autowired
    private NodeRootRepository repository;

    void givenOwner_whenFindFirstByOwner_thenFindAccount() {
        String id = "14556";
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
