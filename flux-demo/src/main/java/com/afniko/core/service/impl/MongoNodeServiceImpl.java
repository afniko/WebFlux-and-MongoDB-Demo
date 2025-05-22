package com.afniko.core.service.impl;

import static java.util.Objects.nonNull;

import com.afniko.core.converter.TypeConverterFacade;
import com.afniko.core.dto.NodeDto;
import com.afniko.core.model.NodeDesc;
import com.afniko.core.model.NodeRoot;
import com.afniko.core.repository.NodeDescRepository;
import com.afniko.core.repository.NodeRootRepository;
import com.afniko.core.service.NodeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MongoNodeServiceImpl implements NodeService {

    private static final Logger LOG = LoggerFactory.getLogger(MongoNodeServiceImpl.class);

    private final NodeRootRepository nodeRootRepository;

    private final NodeDescRepository nodeDescRepository;

    private final TypeConverterFacade converterFacade;

    public MongoNodeServiceImpl(
        NodeRootRepository nodeRootRepository,
        NodeDescRepository nodeDescRepository,
        TypeConverterFacade converterFacade) {
        this.nodeRootRepository = nodeRootRepository;
        this.nodeDescRepository = nodeDescRepository;
        this.converterFacade = converterFacade;
    }

    @Override
    public Mono<NodeDto> save(NodeDto dto) {
        LOG.debug("In save - save node: [{}]", dto);

        if (nonNull(dto.getDescription())) {
            final NodeDesc node = converterFacade.convert(dto, NodeDesc.class);
            return nodeDescRepository.save(node)
                .map(o -> converterFacade.convert(o, NodeDto.class));
        } else {
            final NodeRoot node = converterFacade.convert(dto, NodeRoot.class);
            return nodeRootRepository.save(node)
                .map(o -> converterFacade.convert(o, NodeDto.class));
        }
    }

    @Override
    public Mono<NodeDto> save(Mono<NodeDto> nodeDtoMono) {
        LOG.debug("In save - save node in Mono type");
        return nodeDtoMono
            .flatMap(this::save);

    }

    @Override
    public Flux<NodeDto> findAll() {
        LOG.debug("In findAll - find all nodes");
        return nodeDescRepository.findAll()
            .map(o -> converterFacade.convert(o, NodeDto.class));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        LOG.debug("In deleteById - delete node by id: [{}]", id);
        return nodeRootRepository.deleteById(id)
            .then(nodeDescRepository.deleteById(id))
            .then();
    }
}
