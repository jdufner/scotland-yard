package de.jdufner.scotland.yard;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.factory.GraphDatabaseFacade;
import org.neo4j.server.CommunityBootstrapper;
import org.neo4j.server.NeoServer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Neo4jServer {

  private static File databaseDirectory = new File("./neo4j-data");
  private static File configFile = new File("./config/neo4j.conf");

  public static void main(String[] args) {
    CommunityBootstrapper communityBootstrapper = new CommunityBootstrapper();
    Map<String, String> properties = new HashMap<>();
    properties.put("dbms.connector.http.address", "0.0.0.0:7474");
    properties.put("dbms.security.auth_enabled", "false");
    properties.put("dbms.directories.import", "/../src/main/resources");
    communityBootstrapper.start(databaseDirectory, Optional.of(configFile), properties);
    NeoServer neoServer = communityBootstrapper.getServer();
    GraphDatabaseFacade graphDatabaseFacade = neoServer.getDatabase().getGraph();
    registerShutdownHook(graphDatabaseFacade);

    try (Transaction tx = graphDatabaseFacade.beginTx()) {
      for (int i = 1; i <= 199; i++) {
        graphDatabaseFacade.execute("CREATE (n :Node {number: " + i + "})");
      }

      graphDatabaseFacade.execute("LOAD CSV FROM 'file://" +
          "/SCOTMAP.TXT' AS line " +
          "MATCH (n:Node), (m:Node) " +
          "WHERE n.number = toInteger(line[0]) and m.number = toInteger(line[1]) " +
          "CREATE (n)-[:RELATION {type: line[2]}]->(m) "
      );

      graphDatabaseFacade.execute("match (n:Node)-[r:RELATION]->(m:Node) where r" +
          ".type=\"Bus\" create (n)-[:BUS]->(m) return n, m");

      graphDatabaseFacade.execute("match (n:Node)-[r:RELATION]->(m:Node) where r" +
          ".type=\"Taxi\" create (n)-[:TAXI]->(m) return n, m");

      graphDatabaseFacade.execute("match (n:Node)-[r:RELATION]->(m:Node) where r" +
          ".type=\"Ship\" create (n)-[:SHIP]->(m) return n, m");

      graphDatabaseFacade.execute("match (n:Node)-[r:RELATION]->(m:Node) where r" +
          ".type=\"Underground\" create (n)-[:UNDERGROUND]->(m) return n, m");

      graphDatabaseFacade.execute("match (:Node)-[r:RELATION]->(:Node) delete r");

      // Welcher Knoten hat die meisten Beziehungen?
      // match (n:Node)-[r]-(:Node) return n.number, count(r) as rels order by rels desc

      // Welche Knoten haben wie viele Beziehungen?
      // MATCH (n:Node)-[r]-(m:Node) RETURN n.number, m.number, count(r) order by count(r) desc, n.number, m.number asc

      tx.success();
    }

  }

  private static void registerShutdownHook(final GraphDatabaseService graphDb) {
    // Registers a shutdown hook for the Neo4j instance so that it
    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
    // running application).
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        graphDb.shutdown();
      }
    });
  }

}
