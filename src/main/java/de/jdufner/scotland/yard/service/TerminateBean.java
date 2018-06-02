package de.jdufner.scotland.yard.service;

import javax.annotation.PreDestroy;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.stereotype.Component;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Component
public class TerminateBean {

  private final GraphDatabaseService graphDatabaseService;

  public TerminateBean(final GraphDatabaseService graphDatabaseService) {
    this.graphDatabaseService = graphDatabaseService;
  }

  @PreDestroy
  public void shutdown() {
    graphDatabaseService.shutdown();
  }

}
