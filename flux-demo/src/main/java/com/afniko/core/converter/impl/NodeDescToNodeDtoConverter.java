package com.afniko.core.converter.impl;

import com.afniko.core.converter.TypeConverter;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.model.NodeDesc;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NodeDescToNodeDtoConverter implements TypeConverter<NodeDesc, NodeDto> {

    private ModelMapper mapper;

    public NodeDescToNodeDtoConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

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
        return mapper.map(node, NodeDto.class);
    }

}
