package com.afniko.resource.functional.config;

import com.afniko.resource.functional.handler.NodeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.afniko.Constants.Endpoints;

@Configuration
public class NodeRouter {

    private static final Logger LOG = LoggerFactory.getLogger(NodeRouter.class);

    @Bean
    public RouterFunction<ServerResponse> route(NodeHandler nodeHandler) {
        LOG.debug("In route - set routes with handlers");
        final RequestPredicate routeAllNodes = RequestPredicates.GET(Endpoints.NODE_FUNCTIONAL).and(RequestPredicates.accept(MediaType.APPLICATION_JSON));
        final RequestPredicate routeSaveNode = RequestPredicates.POST(Endpoints.NODE_FUNCTIONAL).and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        return RouterFunctions
                .route(routeAllNodes, nodeHandler::getNodes)
                .andRoute(routeSaveNode, nodeHandler::saveNode);
    }
}
