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
 */

package de.jdufner.scotland.yard.gameboard.service;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
    LOG.info(new Object() {}.getClass().getEnclosingMethod().getName());
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
