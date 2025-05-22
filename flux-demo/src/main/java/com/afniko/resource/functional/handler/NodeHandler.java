package com.afniko.resource.functional.handler;

import com.afniko.core.dto.NodeDto;
import com.afniko.core.service.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class NodeHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NodeHandler.class);

    private final NodeService nodeService;

    public NodeHandler(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public Mono<ServerResponse> getNodes(ServerRequest request) {
        LOG.debug("In getNodes - handle request for get all nodes");
        final Flux<NodeDto> nodes = nodeService.findAll();
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(nodes, NodeDto.class);
    }

    public Mono<ServerResponse> saveNode(ServerRequest request) {
        final Mono<NodeDto> nodeDtoMono = request.bodyToMono(NodeDto.class);
        LOG.debug("In saveNode - handle request for save node");

        final Mono<NodeDto> nodes = nodeService.save(nodeDtoMono);
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(nodes, NodeDto.class);
    }

    public Mono<ServerResponse> deleteNode(ServerRequest request) {
        final String id = request.pathVariable("id");
        LOG.debug("In deleteNode - handle request for delete node with id: [{}]", id);
        
        return nodeService.deleteById(id)
                .then(ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .build());
    }
}
