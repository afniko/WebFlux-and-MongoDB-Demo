package com.afniko.integration;

import static com.afniko.Constants.Endpoints;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.afniko.FluxDemoBootApplication;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.model.NodeDesc;
import com.afniko.core.model.NodeRoot;
import com.afniko.core.repository.NodeDescRepository;
import com.afniko.core.repository.NodeRootRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = FluxDemoBootApplication.class)
class ITNodeRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private NodeDescRepository nodeDescRepository;

    @MockBean
    private NodeRootRepository nodeRootRepository;

    @Test
    void shouldReturnNodeDescDtos() {
        final String id1 = "1";
        final String id2 = "2";
        final String id3 = "3";
        final NodeDto node1 = getNodeDtoDesc(id1);
        final NodeDto node2 = getNodeDtoDesc(id2);
        final NodeDto node3 = getNodeDtoDesc(id3);
        final NodeDesc nodeDesc1 = getNodeDesc(id1);
        final NodeDesc nodeDesc2 = getNodeDesc(id2);
        final NodeDesc nodeDesc3 = getNodeDesc(id3);
        final Flux<NodeDesc> nodeDescFlux = Flux.just(nodeDesc1, nodeDesc2, nodeDesc3);
        final List<NodeDto> nodeList = List.of(node1, node2, node3);

        when(nodeDescRepository.findAll()).thenReturn(nodeDescFlux);

        webTestClient
            .get().uri(Endpoints.NODE_FUNCTIONAL)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(NodeDto.class)
            .hasSize(3)
            .isEqualTo(nodeList);
    }

    @Test
    void whenUpdateNodeDesc_thenReturnUpdatedDto() {
        final String id1 = "1";
        final NodeDto node = getNodeDtoDesc(id1);
        final NodeDesc nodeDesc = getNodeDesc(id1);
        final Mono<NodeDesc> nodeDescMono = Mono.just(nodeDesc);

        when(nodeDescRepository.save(any(NodeDesc.class))).thenReturn(nodeDescMono);

        webTestClient
            .post()
            .uri(Endpoints.NODE_FUNCTIONAL)
            .bodyValue(node)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(NodeDto.class).isEqualTo(node);

    }

    @Test
    void whenUpdateNodeRoot_thenReturnUpdatedDto() {
        final String id1 = "1";
        final NodeDto node = getNodeDtoRoot(id1);
        final NodeRoot nodeDesc = getNodeRoot(id1);
        final Mono<NodeRoot> nodeDescMono = Mono.just(nodeDesc);

        when(nodeRootRepository.save(any(NodeRoot.class))).thenReturn(nodeDescMono);

        webTestClient
            .post()
            .uri(Endpoints.NODE_FUNCTIONAL)
            .bodyValue(node)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(NodeDto.class).isEqualTo(node);

    }

    @Test
    void whenDeleteNodeById_thenReturnOkStatus() {
        final String id = "1";
        when(nodeRootRepository.deleteById(id)).thenReturn(Mono.empty());
        when(nodeDescRepository.deleteById(id)).thenReturn(Mono.empty());

        webTestClient
            .delete()
            .uri(Endpoints.NODE_FUNCTIONAL + "/{id}", id)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody().isEmpty();
    }

    private NodeDto getNodeDtoDesc(String id) {
        return new NodeDto(
            id,
            "name".concat(id),
            "description".concat(id));
    }

    private NodeDto getNodeDtoRoot(String id) {
        return new NodeDto(
            id,
            "name".concat(id),
            null);
    }

    private NodeDesc getNodeDesc(String id) {
        return new NodeDesc(
            id,
            "name".concat(id),
            "description".concat(id));
    }

    private NodeRoot getNodeRoot(String id) {
        return new NodeRoot(
            id,
            "name".concat(id));
    }
}
