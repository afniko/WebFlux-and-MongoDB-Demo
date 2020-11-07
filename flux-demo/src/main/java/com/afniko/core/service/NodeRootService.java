package com.afniko.core.service;

import com.afniko.core.model.NodeRoot;

import java.util.List;

public interface NodeRootService {

    NodeRoot save(NodeRoot nodeRootDto);

    List<NodeRoot> findAll();

}
