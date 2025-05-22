package com.afniko.resource.functional.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.afniko.core.dto.NodeDto;
import com.afniko.core.service.NodeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Map;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class NodeHandlerTest {

    @Mock
    private NodeService nodeService;

    @InjectMocks
    private NodeHandler nodeHandler;

    private NodeDto nodeDto;

    private static final String NODE_ID = "1";

    @BeforeEach
    void init() {
        nodeDto = new NodeDto(NODE_ID, "name");
    }

    @Test
    void shouldReturnNodeDtos() {
        ServerRequest request = MockServerRequest.builder().build();
        when(nodeService.findAll()).thenReturn(Flux.just(nodeDto));

        Mono<ServerResponse> result = nodeHandler.getNodes(request);

        StepVerifier
            .create(result)
            .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSaveNode() {
        ServerRequest request = MockServerRequest.builder()
            .body(Mono.just(nodeDto));
        when(nodeService.save(any(Mono.class))).thenReturn(Mono.just(nodeDto));

        Mono<ServerResponse> result = nodeHandler.saveNode(request);

        StepVerifier
            .create(result)
            .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
            .expectComplete()
            .verify();
    }

    @Test
    void shouldDeleteNode() {
        ServerRequest request = MockServerRequest.builder()
            .pathVariables(Map.of("id", NODE_ID))
            .build();
        when(nodeService.deleteById(NODE_ID)).thenReturn(Mono.empty());

        Mono<ServerResponse> result = nodeHandler.deleteNode(request);

        StepVerifier
            .create(result)
            .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
            .expectComplete()
            .verify();

        verify(nodeService).deleteById(eq(NODE_ID));
    }
}
