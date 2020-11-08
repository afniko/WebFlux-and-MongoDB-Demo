package com.afniko.config;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
@MockBean(MongoClient.class)
public class ContextTestConfig {

}
