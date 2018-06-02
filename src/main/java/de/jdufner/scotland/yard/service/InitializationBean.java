package de.jdufner.scotland.yard.service;

import javax.annotation.PostConstruct;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Component
public class InitializationBean {

  private static final Logger LOG = LoggerFactory.getLogger(InitializationBean.class);

  private final GraphDatabaseService graphDatabaseService;

  public InitializationBean(final GraphDatabaseService graphDatabaseService) {
    this.graphDatabaseService = graphDatabaseService;
  }

  @PostConstruct
  public void initialize() {
    LOG.info(new Object() {
    }.getClass().getEnclosingMethod().getName());
    // TODO: Prüfen ob die TX-Steuerung mit Annotationen gelöst werden kann.
    try (Transaction tx = graphDatabaseService.beginTx()) {
      final Result execute = graphDatabaseService.execute("MATCH (n:Node) WHERE n.number = 1 " +
          "RETURN n");
      if (execute.hasNext()) {
        tx.close();
        return;
      }

      for (int i = 1; i <= 199; i++) {
        graphDatabaseService.execute("CREATE (n:Node {number: " + i + "})");
      }

      graphDatabaseService.execute("LOAD CSV FROM 'file://" +
          "/SCOTMAP.TXT' AS line " +
          "MATCH (n:Node), (m:Node) " +
          "WHERE n.number = toInteger(line[0]) and m.number = toInteger(line[1]) " +
          "CREATE UNIQUE (n)-[:RELATION {type: line[2]}]->(m) "
      );

      graphDatabaseService.execute("match (n:Node)-[r:RELATION]->(m:Node) where r" +
          ".type=\"Bus\" create (n)-[:BUS]->(m) return n, m");

      graphDatabaseService.execute("match (n:Node)-[r:RELATION]->(m:Node) where r" +
          ".type=\"Taxi\" create (n)-[:TAXI]->(m) return n, m");

      graphDatabaseService.execute("match (n:Node)-[r:RELATION]->(m:Node) where r" +
          ".type=\"Ship\" create (n)-[:SHIP]->(m) return n, m");

      graphDatabaseService.execute("match (n:Node)-[r:RELATION]->(m:Node) where r" +
          ".type=\"Underground\" create (n)-[:UNDERGROUND]->(m) return n, m");

      graphDatabaseService.execute("match (:Node)-[r:RELATION]->(:Node) delete r");

      // Welcher Knoten hat die meisten Beziehungen?
      // match (n:Node)-[r]-(:Node) return n.number, count(r) as rels order by rels desc

      // Welche Knoten haben wie viele Beziehungen?
      // MATCH (n:Node)-[r]-(m:Node) RETURN n.number, m.number, count(r) order by count(r) desc, n.number, m.number asc

      tx.success();
    }
  }

}
