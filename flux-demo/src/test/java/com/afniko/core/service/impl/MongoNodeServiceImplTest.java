package com.afniko.core.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.afniko.core.converter.TypeConverterFacade;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.model.NodeDesc;
import com.afniko.core.model.NodeRoot;
import com.afniko.core.repository.NodeDescRepository;
import com.afniko.core.repository.NodeRootRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Predicate;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class MongoNodeServiceImplTest {

    @Mock
    private NodeRootRepository nodeRootRepository;

    @Mock
    private NodeDescRepository nodeDescRepository;

    @Mock
    private TypeConverterFacade converterFacade;

    @InjectMocks
    private MongoNodeServiceImpl service;

    @Test
    void shouldFindAllNodes() {
        final String id1 = "1";
        final String id2 = "2";
        final String id3 = "3";
        final NodeDto node1 = getNodeDtoDesc(id1);
        final NodeDto node2 = getNodeDtoDesc(id2);
        final NodeDto node3 = getNodeDtoDesc(id3);
        final Flux<NodeDto> expected = Flux.just(node1, node2, node3);
        final NodeDesc nodeDesc1 = getNodeDesc(id1);
        final NodeDesc nodeDesc2 = getNodeDesc(id2);
        final NodeDesc nodeDesc3 = getNodeDesc(id3);

        final Flux<NodeDesc> nodeDescFlux = Flux.just(nodeDesc1, nodeDesc2, nodeDesc3);

        when(nodeDescRepository.findAll()).thenReturn(nodeDescFlux);
        when(converterFacade.convert(any(NodeDesc.class), eq(NodeDto.class))).thenReturn(node1, node2, node3);

        Flux<NodeDto> nodeDtoFlux = service.findAll();

        Predicate<NodeDto> match = node -> expected.any(saveItem -> saveItem.equals(node)).block();
        StepVerifier
            .create(nodeDtoFlux)
            .expectNextMatches(match)
            .expectNextMatches(match)
            .expectNextMatches(match)
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSaveNodeDesc() {
        String id = "1";
        final NodeDto nodeDto = getNodeDtoDesc(id);
        final NodeDesc nodeDesc = getNodeDesc(id);
        final Mono<NodeDesc> nodeDescMono = Mono.just(nodeDesc);
        Mono<NodeDto> nodeDtoMono = Mono.just(nodeDto);
        when(converterFacade.convert(nodeDto, NodeDesc.class)).thenReturn(nodeDesc);
        when(converterFacade.convert(any(NodeDesc.class), eq(NodeDto.class))).thenReturn(nodeDto);
        when(nodeDescRepository.save(nodeDesc)).thenReturn(nodeDescMono);

        final Mono<NodeDto> result = service.save(nodeDtoMono);

        StepVerifier
            .create(result)
            .expectNext(nodeDto)
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSaveNodeDtoDesc() {
        String id = "1";
        final NodeDto nodeDto = getNodeDtoDesc(id);
        final NodeDesc nodeDesc = getNodeDesc(id);
        final Mono<NodeDesc> nodeDescMono = Mono.just(nodeDesc);

        when(converterFacade.convert(nodeDto, NodeDesc.class)).thenReturn(nodeDesc);
        when(converterFacade.convert(any(NodeDesc.class), eq(NodeDto.class))).thenReturn(nodeDto);
        when(nodeDescRepository.save(nodeDesc)).thenReturn(nodeDescMono);

        final Mono<NodeDto> result = service.save(nodeDto);

        verify(converterFacade).convert(any(NodeDto.class), eq(NodeDesc.class));
        verifyNoMoreInteractions(converterFacade);

        StepVerifier
            .create(result)
            .expectNext(nodeDto)
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSaveNodeRoot() {
        String id = "1";
        final NodeDto nodeDto = getNodeDtoRoot(id);
        final NodeRoot nodeRoot = getNodeRoot(id);
        final Mono<NodeRoot> nodeRootMono = Mono.just(nodeRoot);
        Mono<NodeDto> nodeDtoMono = Mono.just(nodeDto);
        when(converterFacade.convert(nodeDto, NodeRoot.class)).thenReturn(nodeRoot);
        when(converterFacade.convert(any(NodeRoot.class), eq(NodeDto.class))).thenReturn(nodeDto);
        when(nodeRootRepository.save(nodeRoot)).thenReturn(nodeRootMono);

        final Mono<NodeDto> result = service.save(nodeDtoMono);

        StepVerifier
            .create(result)
            .expectNext(nodeDto)
            .expectComplete()
            .verify();
    }

    @Test
    void shouldSaveNodeDtoRoot() {
        String id = "1";
        final NodeDto nodeDto = getNodeDtoRoot(id);
        final NodeRoot nodeRoot = getNodeRoot(id);
        final Mono<NodeRoot> nodeRootMono = Mono.just(nodeRoot);

        when(converterFacade.convert(nodeDto, NodeRoot.class)).thenReturn(nodeRoot);
        when(converterFacade.convert(any(NodeRoot.class), eq(NodeDto.class))).thenReturn(nodeDto);
        when(nodeRootRepository.save(nodeRoot)).thenReturn(nodeRootMono);

        final Mono<NodeDto> result = service.save(nodeDto);

        verify(converterFacade).convert(any(NodeDto.class), eq(NodeRoot.class));
        verifyNoMoreInteractions(converterFacade);

        StepVerifier
            .create(result)
            .expectNext(nodeDto)
            .expectComplete()
            .verify();
    }

    @Test
    void shouldDeleteNodeById() {
        String id = "1";

        when(nodeRootRepository.deleteById(id)).thenReturn(Mono.empty());
        when(nodeDescRepository.deleteById(id)).thenReturn(Mono.empty());

        Mono<Void> resultMono = service.deleteById(id);

        StepVerifier
            .create(resultMono)
            .expectComplete()
            .verify();

        verify(nodeRootRepository).deleteById(id);
        verify(nodeDescRepository).deleteById(id);
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
