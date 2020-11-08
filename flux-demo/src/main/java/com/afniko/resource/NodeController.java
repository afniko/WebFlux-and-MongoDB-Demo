package com.afniko.resource;

import com.afniko.core.dto.NodeDto;
import com.afniko.core.service.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.afniko.Constants.Endpoints;

@RestController
@RequestMapping(Endpoints.NODE)
public class NodeController {

    private static final Logger LOG = LoggerFactory.getLogger(NodeController.class);

    private final NodeService nodeService;

    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<NodeDto> getNodes() {
        LOG.debug("In getNodes - GET request for get message");
        return nodeService.findAll();
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<NodeDto> saveNode(@RequestBody NodeDto node) {
        LOG.debug("In saveNode - POST save node: [{}]", node);
        return nodeService.save(node);
    }

}
