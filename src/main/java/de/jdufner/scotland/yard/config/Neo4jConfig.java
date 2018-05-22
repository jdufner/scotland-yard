/*
 * Scotland Yard is a simulation of the board game.
 * Copyright (C) 2008-2018  Juergen Dufner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.jdufner.scotland.yard.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.factory.GraphDatabaseFacade;
import org.neo4j.server.CommunityBootstrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Configuration
public class Neo4jConfig {

  private static File databaseDirectory = new File("./neo4j-data");
  private static File configFile = new File("./config/neo4j.conf");

  @Bean
  public GraphDatabaseService graphDatabaseService() {
    final GraphDatabaseFacade graphDatabaseFacade = bootstrapGraphDatabase();
    populateGraphDatabase(graphDatabaseFacade);
    registerShutdownHook(graphDatabaseFacade);
    return graphDatabaseFacade;
  }

  private GraphDatabaseFacade bootstrapGraphDatabase() {
    final CommunityBootstrapper communityBootstrapper = new CommunityBootstrapper();
    final Map<String, String> properties = new HashMap<>();
    properties.put("dbms.connector.http.address", "0.0.0.0:7474");
    properties.put("dbms.security.auth_enabled", "false");
    properties.put("dbms.directories.import", "../src/main/resources");
    communityBootstrapper.start(databaseDirectory, Optional.of(configFile), properties);
    return communityBootstrapper.getServer().getDatabase()
        .getGraph();
  }

  private void populateGraphDatabase(final GraphDatabaseService graphDatabaseService) {
    try (Transaction tx = graphDatabaseService.beginTx()) {
      Result execute = graphDatabaseService.execute("MATCH (n:Node) WHERE n.number = 1 RETURN n");
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

  private void registerShutdownHook(final GraphDatabaseService graphDb) {
    // Registers a shutdown hook for the Neo4j instance so that it
    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
    // running application).
    Runtime.getRuntime().addShutdownHook(new Thread(() -> graphDb.shutdown()));
  }

}
