package com.afniko.core.converter.impl;

import com.afniko.core.converter.TypeConverter;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.model.NodeRoot;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NodeRootToNodeDtoConverter implements TypeConverter<NodeRoot, NodeDto> {

    private ModelMapper mapper;

    public NodeRootToNodeDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

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
        return mapper.map(node, NodeDto.class);
    }

}
