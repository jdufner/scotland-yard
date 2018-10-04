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

import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.Ticket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

//  public Position findeNachbarAmWeitestenEntferntVonDetektiven() {
//    final List<Weg> wege = new ArrayList<>();
//    try (final Transaction tx = graphDatabaseService.beginTx()) {
//      Result result = graphDatabaseService.execute("MATCH (n:MRX)-[:TAXI|BUS|UNDERGROUND]-" +
//          "(m:Node), p=shortestPath((m)-[:TAXI|BUS|UNDERGROUND*1..]-(d:DETEKTIV)) RETURN n" +
//          ".number, m.number, d.number, length(p) ORDER BY m.number, d.number asc, length(p) desc");
//
//      while (result.hasNext()) {
//        wege.add(buildWeg(result.next(), "m.number", "d.number", "length(p)"));
//      }
//      tx.success();
//    }
//    LOG.debug("Alle Wege von den Nachbarn zu allen Detektiven");
//    LOG.debug("{}", wege);
//
//    Map<Position, Optional<Weg>> nachbar2KuerzesteDistanzZumNaechstenDetektiv = wege.stream()
//        .collect(Collectors.groupingBy(Weg::getStart,
//            Collectors.minBy(Comparator.comparing(Weg::getLaenge))));
//    LOG.debug("Nachbar mit jeweils kürzester Distanz zu nächsten Detective");
//    LOG.debug("{}", nachbar2KuerzesteDistanzZumNaechstenDetektiv);
//
//    Optional<Weg> weg = nachbar2KuerzesteDistanzZumNaechstenDetektiv.values().stream()
//        .filter(Optional::isPresent)
//        .map(Optional::get)
//        .collect(Collectors.maxBy(Comparator.comparing(Weg::getLaenge)));
//    LOG.debug("Größte Minimaldistanz zu allen Detektiven");
//    LOG.debug("{}", weg.get().getLaenge());
//
//    List<Weg> nachbarnMitGroessterDistanzZuAllenDetektiven =
//        nachbar2KuerzesteDistanzZumNaechstenDetektiv.values()
//            .stream()
//            .filter(Optional::isPresent)
//            .map(Optional::get)
//            .filter(weg1 -> weg1.getLaenge() == weg.get().getLaenge())
//            .collect(Collectors.toList());
//    LOG.debug("Nachbarn mit größter Distanz zu allen Detektiven");
//    LOG.debug("{}", nachbarnMitGroessterDistanzZuAllenDetektiven);
//
//    Map<Position, Double> avg = nachbarnMitGroessterDistanzZuAllenDetektiven.stream()
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
//  }

//  private Weg buildWeg(final Map<String, Object> row, final String start, final String ende,
//                       final String laenge) {
//    return new Weg(new Position(Integer.parseInt(row.get(start).toString())),
//        new Position(Integer.parseInt(row.get(ende).toString())),
//        Integer.parseInt(row.get(laenge).toString()));
//  }

//  public Position findeWegZuUndergroundInAnzahlZuegen(final Player player,
//                                                      final int anzahlZuege) {
//    assert anzahlZuege > 0 : "Anzahl der Züge muss größer als 0 sein.";
//    assert anzahlZuege < 3 : "Anzahl der Züge muss kleiner als 3 sein.";
//
//    final List<Weg> wege = new ArrayList<>();
//    try (final Transaction tx = graphDatabaseService.beginTx()) {
//      final Result result = graphDatabaseService.execute("MATCH (n:Node)-[]-(m:Node) " +
//          "WHERE n.number=" + player.getCurrentPosition().getPosition() + " " +
//          "RETURN n.number, m.number, 1 as laenge");
//      while (result.hasNext()) {
//        wege.add(buildWeg(result.next(), "n.number", "m.number", "laenge"));
//      }
//      tx.success();
//    }
//
//    LOG.debug("{}", wege);
//
//    return wege.get((int) (Math.random() * wege.size())).getEnde();
//  }

//  public Position findeKuerzestenWegZuMrX(final Player player,
//                                          final Position letzteBekanntePositionVonMrX,
//                                          final int anzahlVergangeneRundenSeitSichMrXGezeigthat) {
//    assert anzahlVergangeneRundenSeitSichMrXGezeigthat >= 0 :
//        "Anzahl der Runden muss größer oder gleich als 0 sein.";
//    assert anzahlVergangeneRundenSeitSichMrXGezeigthat <= 5 :
//        "Anzahl der Runden muss kleiner oder gleich als 5 sein.";
//
//    ermittleMoeglichePositionenVonMrX(letzteBekanntePositionVonMrX,
//        anzahlVergangeneRundenSeitSichMrXGezeigthat);
//
//    return null;
//  }

//  private List<Position> ermittleMoeglichePositionenVonMrX(
//      final Position letzteBekanntePositionVonMrX,
//      final int anzahlVergangeneRundenSeitSichMrXGezeigthat) {
//    if (anzahlVergangeneRundenSeitSichMrXGezeigthat == 0) {
//      return Collections.singletonList(letzteBekanntePositionVonMrX);
//    }
//    return null;
//  }

  public List<Move> findAllPossibleMoves(Position position) {
    List<Move> possibleMoves = new ArrayList<>();
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      final Result result = graphDatabaseService.execute("MATCH " +
          "(n:Node " + position.asNodeAttribute() + ")-[r]-(m:Node) " +
          "RETURN n.number, type(r), m.number");
      while (result.hasNext()) {
        Map<String, Object> objectMap = result.next();
        possibleMoves.add(new Move(
            null,
            new Position(Integer.parseInt(objectMap.get("n.number").toString())),
            new Position(Integer.parseInt(objectMap.get("m.number").toString())),
            Ticket.Factory.create(objectMap.get("type(r)").toString())
        ));
      }
      tx.success();
      return possibleMoves;
    }
    //return emptyList();
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

  public List<Position> findPositionsNextToMrxFarAwayFromDetectives(Position mrxPosition, List<Position> detectivesPosition) {
    return null;
  }
}
