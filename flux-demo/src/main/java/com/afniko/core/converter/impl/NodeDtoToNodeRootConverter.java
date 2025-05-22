package com.afniko.core.converter.impl;

import com.afniko.core.converter.TypeConverter;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.model.NodeRoot;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NodeDtoToNodeRootConverter implements TypeConverter<NodeDto, NodeRoot> {

    private ModelMapper mapper;

    public NodeDtoToNodeRootConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Class<NodeDto> getSourceClass() {
        return NodeDto.class;
    }

    @Override
    public Class<NodeRoot> getTargetClass() {
        return NodeRoot.class;
    }

    @Override
    public NodeRoot convert(NodeDto dto) {
        return mapper.map(dto, NodeRoot.class);
    }

}
