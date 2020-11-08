package com.afniko.core.service;

import com.afniko.core.dto.NodeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NodeService {

    Mono<NodeDto> save(NodeDto nodeRootDto);

    Flux<NodeDto> findAll();

}
