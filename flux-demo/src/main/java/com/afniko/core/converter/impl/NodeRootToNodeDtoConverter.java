package com.afniko.core.converter.impl;

import com.afniko.core.converter.TypeConverter;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.model.NodeRoot;
import org.springframework.stereotype.Component;

@Component
public class NodeRootToNodeDtoConverter implements TypeConverter<NodeRoot, NodeDto> {

    @Override
    public Class<NodeRoot> getSourceClass() {
        return NodeRoot.class;
    }

    @Override
    public Class<NodeDto> getTargetClass() {
        return NodeDto.class;
    }

    @Override
    public NodeDto convert(NodeRoot node) {
        return new NodeDto(
                node.getId(),
                node.getName()
        );
    }

}
