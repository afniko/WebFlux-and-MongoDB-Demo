package com.afniko.core.repository;

import com.afniko.core.model.NodeRoot;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NodeRootRepository extends ReactiveMongoRepository<NodeRoot, String> {

}
