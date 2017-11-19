package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spiel.Weg;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class SpielService {

  private final GraphDatabaseService graphDatabaseService;

  public SpielService(final GraphDatabaseService graphDatabaseService) {
    this.graphDatabaseService = graphDatabaseService;
  }

  public void entferneSpieler(final Collection<Spieler> spielers) {
    spielers.stream().forEach(spieler -> {
      try (final Transaction tx = graphDatabaseService.beginTx()) {
        graphDatabaseService.execute("MATCH (n:" + spieler.name().toUpperCase() + ") REMOVE n:"
            + spieler.name().toUpperCase());
        tx.success();
      }
    });
  }

  public void setzeSpieler(final Collection<Spieler> spielers) {
    spielers.stream().forEach(spieler -> {
      try (final Transaction tx = graphDatabaseService.beginTx()) {
        graphDatabaseService.execute("MATCH (n:Node) WHERE n.number = " + spieler.letztePosition
            ().getPosition() + " SET n:" + spieler.name().toUpperCase());
        tx.success();
      }
    });
  }

  public void ermittleKuerzesteDistanzenZwischenJeweilsAllenKnoten(final Spiel spiel) {
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      for (int n = 1; n <= 198; n++) {
        for (int m = n + 1; m <= 199; m++) {
          Result result = graphDatabaseService.execute("MATCH p=shortestPath((n:Node)-" +
              "[:TAXI|BUS|UNDERGROUND*1..10]-(m:Node)) WHERE n.number = " + n + " AND m.number = " +
              m + " RETURN DISTINCT n.number, m.number, length(p)");
          if (result.hasNext()) {
            final Weg weg = buildWeg(result.next(), "n.number", "m.number", "length(p)");
          }
        }
      }
      tx.close();
    }
  }

  public void findeNachbarAmWeitestenEntferntVonDetektiven() {
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      Result result = graphDatabaseService.execute("MATCH (n:MRX)-[:TAXI|BUS|UNDERGROUND]-" +
          "(m:Node), p=shortestPath((m)-[:TAXI|BUS|UNDERGROUND*1..]-(d:DETEKTIV)) RETURN n" +
          ".number, m.number, d.number, length(p) ORDER BY m.number, d.number asc, length(p) desc");
      List<Weg> wege = new ArrayList<>();
      while (result.hasNext()) {
        wege.add(buildWeg(result.next(), "m.number", "d.number", "length(p)"));
      }
      wege.stream().collect(Collectors.groupingBy(Weg::getStart));

      Map<Position, Optional<Weg>> collect = wege.stream().collect(Collectors.groupingBy(Weg::getStart, Collectors.minBy(Comparator.comparing(Weg::getLaenge))));
      System.out.println(collect);

      Map<Position, Double> avg = wege.stream().collect(Collectors.groupingBy
          (Weg::getStart, Collectors.averagingInt(Weg::getLaenge)));
      System.out.println(avg);
      tx.success();
    }
  }

  private Weg buildWeg(final Map<String, Object> row, final String start, final String ende, final String laenge) {
    return new Weg(new Position(Integer.parseInt(row.get(start).toString())),
        new Position(Integer.parseInt(row.get(ende).toString())),
        Integer.parseInt(row.get(laenge).toString()));
  }

}
