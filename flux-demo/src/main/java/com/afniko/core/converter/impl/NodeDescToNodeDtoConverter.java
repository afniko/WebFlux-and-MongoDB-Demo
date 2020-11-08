package com.afniko.core.converter.impl;

import com.afniko.core.converter.TypeConverter;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.model.NodeDesc;
import org.springframework.stereotype.Component;

@Component
public class NodeDescToNodeDtoConverter implements TypeConverter<NodeDesc, NodeDto> {

    @Override
    public Class<NodeDesc> getSourceClass() {
        return NodeDesc.class;
    }

    @Override
    public Class<NodeDto> getTargetClass() {
        return NodeDto.class;
    }

    @Override
    public NodeDto convert(NodeDesc node) {
        return new NodeDto(
                node.getId(),
                node.getName(),
                node.getDescription()
        );
    }

}
