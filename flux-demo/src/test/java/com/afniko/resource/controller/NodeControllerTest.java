package com.afniko.resource.controller;

import com.afniko.FluxDemoBootApplication;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.service.NodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.afniko.Constants.Endpoints;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FluxDemoBootApplication.class)
class NodeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private NodeService nodeService;

    @Test
    void shouldReturnDtos() {
        final String id1 = "1";
        final String id2 = "2";
        final String id3 = "3";
        final NodeDto node1 = getNodeDtoDesc(id1);
        final NodeDto node2 = getNodeDtoDesc(id2);
        final NodeDto node3 = getNodeDtoDesc(id3);
        final Flux<NodeDto> expected = Flux.just(node1, node2, node3);
        final List<NodeDto> nodeList = List.of(node1, node2, node3);

        when(nodeService.findAll()).thenReturn(expected);

        webTestClient
                .get().uri(Endpoints.NODE_CONTROLLER)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(NodeDto.class)
                .hasSize(3)
                .isEqualTo(nodeList);
    }

    @Test
    void whenUpdateNode_thenReturnUpdatedDto() {
        final String id1 = "1";
        final NodeDto node = getNodeDtoDesc(id1);
        final Mono<NodeDto> expected = Mono.just(node);

        when(nodeService.save(node)).thenReturn(expected);

        webTestClient
                .post()
                .uri(Endpoints.NODE_CONTROLLER)
                .bodyValue(node)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(NodeDto.class).isEqualTo(node);

    }

    private NodeDto getNodeDtoDesc(String id) {
        return new NodeDto(
                id,
                "name".concat(id),
                "description".concat(id));
    }

}
