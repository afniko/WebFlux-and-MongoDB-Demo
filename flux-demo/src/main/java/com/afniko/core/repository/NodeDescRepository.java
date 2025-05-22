package com.afniko.core.repository;

import com.afniko.core.model.NodeDesc;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NodeDescRepository extends ReactiveMongoRepository<NodeDesc, String> {

}
