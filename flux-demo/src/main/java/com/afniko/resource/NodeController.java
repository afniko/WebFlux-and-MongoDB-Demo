package com.afniko.resource;

import com.afniko.core.model.NodeRoot;
import com.afniko.core.service.NodeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.afniko.resource.NodeController.URL;

@RestController
@RequestMapping(URL)
public class NodeController {

    private static final Logger LOG = LoggerFactory.getLogger(NodeController.class);

    static final String URL = "/node";

    private final NodeRootService nodeRootService;

    public NodeController(NodeRootService nodeRootService) {
        this.nodeRootService = nodeRootService;
    }

    @GetMapping
    public List<NodeRoot> getTestMessage() {
        LOG.debug("In getTestMessage - GET request for get message");

        return nodeRootService.findAll();
    }

    @PostMapping
    public NodeRoot saveNode(@RequestBody NodeRoot nodeRoot) {
        LOG.debug("In saveNode - POST save nodeRoot: [{}]", nodeRoot);
        return nodeRootService.save(nodeRoot);
    }
}
