package com.afniko.core.service.impl;

import com.afniko.core.model.NodeRoot;
import com.afniko.core.repository.NodeRootRepository;
import com.afniko.core.service.NodeRootService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoNodeRootServiceImpl implements NodeRootService {

    private static final Logger LOG = LoggerFactory.getLogger(MongoNodeRootServiceImpl.class);

    private final NodeRootRepository nodeRootRepository;

    public MongoNodeRootServiceImpl(NodeRootRepository nodeRootRepository) {
        this.nodeRootRepository = nodeRootRepository;
    }

    @Override
    public NodeRoot save(NodeRoot nodeRoot) {
        LOG.debug("In save - save NodeRoot: [{}]", nodeRoot);
        return nodeRootRepository.save(nodeRoot);
    }

    @Override
    public List<NodeRoot> findAll() {
        LOG.debug(" IN findAll - find all nodesRoot");
        return nodeRootRepository.findAll();
    }
}
