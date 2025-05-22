package com.afniko.core.converter.impl;

import com.afniko.core.converter.TypeConverter;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.model.NodeDesc;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class NodeDtoToNodeDescConverter implements TypeConverter<NodeDto, NodeDesc> {

    private ModelMapper mapper;

    public NodeDtoToNodeDescConverter(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Class<NodeDto> getSourceClass() {
        return NodeDto.class;
    }

    @Override
    public Class<NodeDesc> getTargetClass() {
        return NodeDesc.class;
    }

    @Override
    public NodeDesc convert(NodeDto dto) {
        return mapper.map(dto, NodeDesc.class);
    }

}
