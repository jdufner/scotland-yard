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

import static java.util.Collections.singletonList;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.toList;

import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.move.Path;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.Ticket;
import de.jdufner.scotland.yard.gameboard.model.Route;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Dieser Service führt alle Zugriffe auf das darunterliegende Spielbrett, sprich Graphen aus.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class BoardService {

  private static final Logger LOG = LoggerFactory.getLogger(BoardService.class);

  private final GraphDatabaseService graphDatabaseService;

  public BoardService(final GraphDatabaseService graphDatabaseService) {
    this.graphDatabaseService = graphDatabaseService;
  }

//  public void ermittleKuerzesteDistanzenZwischenJeweilsAllenKnoten(final Game game) {
//    try (final Transaction tx = graphDatabaseService.beginTx()) {
//      for (int n = 1; n <= 198; n++) {
//        for (int m = n + 1; m <= 199; m++) {
//          Result result = graphDatabaseService.execute("MATCH p=shortestPath((n:Node)-" +
//              "[:TAXI|BUS|UNDERGROUND*1..10]-(m:Node)) WHERE n.number = " + n + " AND m.number = " +
//              m + " RETURN DISTINCT n.number, m.number, length(p)");
//          if (result.hasNext()) {
//            final Weg weg = buildWeg(result.next(), "n.number", "m.number", "length(p)");
//          }
//        }
//      }
//      tx.close();
//    }
//  }

  public Position findPositionsNextToMrxFarAwayFromDetectives(final Position mrxPosition, final List<Position> detectivesPosition) {
    final List<Route> routes = new ArrayList<>();
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      Result result = graphDatabaseService.execute("MATCH (n:Node)-[:TAXI|BUS|UNDERGROUND]-" +
          "(m:Node), p=shortestPath((m)-[:TAXI|BUS|UNDERGROUND*1..]-(d:Node)) " +
          "WHERE n.number=" + mrxPosition.getPosition() + " " +
          "AND ( " + buildWhereClauseFromPositions("d", detectivesPosition) + " ) " +
          "RETURN n.number, m.number, d.number, length(p) " +
          "ORDER BY m.number, d.number asc, length(p) desc");

      while (result.hasNext()) {
        routes.add(buildRoute(result.next(), "m.number", "d.number", "length(p)"));
      }
      tx.success();
    }
    LOG.debug("All routes to from {} to {}: {}", mrxPosition, detectivesPosition, routes);

    Map<Position, Optional<Route>> neighbor2shortestPath = routes.stream()
        .collect(groupingBy(Route::getStart, minBy(comparing(Route::getLength))));
    LOG.debug("Neighbor to shortest path {}", neighbor2shortestPath);

    Optional<Route> route = neighbor2shortestPath.values().stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(maxBy(comparing(Route::getLength)));
    LOG.debug("Distance of longest routes {}", route);

    List<Route> neighborsWithLongestRoutes =
        neighbor2shortestPath.values().stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(weg1 -> weg1.getLength() == route.get().getLength())
            .collect(toList());
    LOG.debug("Neighbor with longest routes {}", neighborsWithLongestRoutes);

//    Map<Position, Double> avg = neighborsWithLongestRoutes.stream()
//        .collect(Collectors.groupingBy(Weg::getStart, Collectors.averagingInt(Weg::getLaenge)));
//    LOG.debug("Nachbar mit durchschnittlicher Distanz zu allen Detektiven");
//    LOG.debug("{}", avg);
//
//    Optional<Map.Entry<Position, Double>> optional = avg.entrySet().stream()
//        .collect(Collectors.maxBy(Comparator.comparing(Map.Entry::getValue)));
//    Position position = optional.get().getKey();
//    LOG.debug("Nächste Position");
//    LOG.debug("{}", position);
//
//    return position;
    return null;
  }

  private String buildWhereClauseFromPositions(String nodeAlias, List<Position> positions) {
    return positions.stream()
        .map(position -> buildWhereClauseFromPosition(nodeAlias, position))
        .collect(joining(" OR "));
  }

  private String buildWhereClauseFromPosition(String nodeAlias, Position position) {
    return nodeAlias + ".number=" + position.getPosition() + " ";
  }

  private Route buildRoute(final Map<String, Object> row, final String start, final String end,
                           final String length) {
    return new Route(new Position(Integer.parseInt(row.get(start).toString())),
        new Position(Integer.parseInt(row.get(end).toString())),
        Integer.parseInt(row.get(length).toString()));
  }

//  public Position findeWegZuUndergroundInAnzahlZuegen(final Position start,
//                                                      final int anzahlZuege) {
//    assert anzahlZuege > 0 : "Anzahl der Züge muss größer als 0 sein.";
//    assert anzahlZuege < 3 : "Anzahl der Züge muss kleiner als 3 sein.";
//
//    final List<Path> pathes = new ArrayList<>();
//    try (final Transaction tx = graphDatabaseService.beginTx()) {
//      final Result result = graphDatabaseService.execute("MATCH (n:Node)-[r]-(m:Node) " +
//          "WHERE n.number=" + start.getPosition() + " " +
//          "RETURN n.number, r.type, m.number, 1 as laenge");
//      while (result.hasNext()) {
//        pathes.add(buildWeg(result.next(), "n.number", "m.number", "r.type"));
//      }
//      tx.success();
//    }
//
//    LOG.debug("{}", pathes);
//
//    return pathes.get((int) (Math.random() * pathes.size())).getEnd();
//  }

//  public Position findeKuerzestenWegZuMrX(final Player player,
//                                          final Position letzteBekanntePositionVonMrX,
//                                          final int anzahlVergangeneRundenSeitSichMrXGezeigthat) {
//    assert anzahlVergangeneRundenSeitSichMrXGezeigthat >= 0 :
//        "Anzahl der Runden muss größer oder gleich als 0 sein.";
//    assert anzahlVergangeneRundenSeitSichMrXGezeigthat <= 5 :
//        "Anzahl der Runden muss kleiner oder gleich als 5 sein.";
//
//    determinePossiblePositionsByTickets(letzteBekanntePositionVonMrX,
//        anzahlVergangeneRundenSeitSichMrXGezeigthat);
//
//    return null;
//  }

  public List<Position> determinePossiblePositionsByTickets(
      final Position position,
      final Ticket... tickets) {
    if (tickets == null || tickets.length == 0) {
      return singletonList(position);
    }

    Set<Position> positions = IntStream.range(0, tickets.length)
        .mapToObj(i -> determinePositions(position, i + 1, tickets))
        .flatMap(List::stream)
        .collect(Collectors.toSet());

//    Set<Position> positions = new HashSet<>();
//    for (int i = 0; i < tickets.length; i++) {
//      positions.addAll(determinePositions(position, i + 1, tickets));
//    }

    return new ArrayList<>(positions);
  }

  private List<Position> determinePositions(final Position position,
                                            final int index, final Ticket... tickets) {
    final List<Position> positions = new ArrayList<>();
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      final Result result = graphDatabaseService.execute("MATCH " +
          "(n:Node " + position.asNodeAttribute() + ")" + buildRelation(index, tickets) + "(m:Node) " +
          "RETURN m.number");
      while (result.hasNext()) {
        final Map<String, Object> objectMap = result.next();
        positions.add(new Position(Integer.parseInt(objectMap.get("m.number").toString())));
      }
      tx.success();
    } finally {
      return unmodifiableList(positions);
    }
  }

  private String buildRelation(final int index, final Ticket... tickets) {
    String relation = IntStream.range(0, index)
        .mapToObj(i -> {
          if (i + 1 < index) {
            return "-[" + tickets[i].asRelation() + "]-()";
          } else {
            return "-[" + tickets[i].asRelation() + "]-";
          }
        })
        .collect(joining());

//    String relation = "";
//    for (int i = 0; i < index; i++) {
//      relation += "-[" + tickets[i].asRelation() + "]-";
//      if (i + 1 < index) {
//        relation += "()";
//      }
//    }

    return relation;
  }

  public List<Path> findAllPathes(final Position position) {
    final List<Path> possiblePathes = new ArrayList<>();
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      final Result result = graphDatabaseService.execute("MATCH " +
          "(n:Node " + position.asNodeAttribute() + ")-[r]-(m:Node) " +
          "RETURN n.number, type(r), m.number");
      while (result.hasNext()) {
        Map<String, Object> objectMap = result.next();
        possiblePathes.add(new Path(
            new Position(Integer.parseInt(objectMap.get("n.number").toString())),
            new Position(Integer.parseInt(objectMap.get("m.number").toString())),
            Ticket.Factory.create(objectMap.get("type(r)").toString())
        ));
      }
      tx.success();
    } finally {
      return unmodifiableList(possiblePathes);
    }
  }

  public boolean isMoveValid(Move move) {
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      final Result result = graphDatabaseService.execute("MATCH " +
          "(n:Node " + move.getStart().asNodeAttribute() + ")-[" + move.getTicket().asRelation() + "]-(m:Node " + move.getEnd().asNodeAttribute() + ") " +
          "RETURN n.number, m.number");
      if (result.hasNext()) {
        return true;
      }
      tx.success();
    }
    return false;
  }

}
