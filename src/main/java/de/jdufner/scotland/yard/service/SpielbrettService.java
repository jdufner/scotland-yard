package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spiel.Weg;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Dieser Service führt alle Zugriffe auf das darunterliegende Spielbrett, sprich Graphen aus.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class SpielbrettService {

  private static final Logger LOG = LoggerFactory.getLogger(SpielbrettService.class);

  private final GraphDatabaseService graphDatabaseService;

  public SpielbrettService(final GraphDatabaseService graphDatabaseService) {
    this.graphDatabaseService = graphDatabaseService;
  }

  private void entferneSpieler(final Collection<Spieler> spielers) {
    spielers.stream().forEach(spieler -> {
      try (final Transaction tx = graphDatabaseService.beginTx()) {
        graphDatabaseService.execute("MATCH (n:" + spieler.name().toUpperCase() + ") REMOVE n:"
            + spieler.name().toUpperCase());
        tx.success();
      }
    });
  }

  public void setzeSpieler(final Collection<Spieler> spielers) {
    entferneSpieler(spielers);
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

  public Position findeNachbarAmWeitestenEntferntVonDetektiven() {
    final List<Weg> wege = new ArrayList<>();
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      Result result = graphDatabaseService.execute("MATCH (n:MRX)-[:TAXI|BUS|UNDERGROUND]-" +
          "(m:Node), p=shortestPath((m)-[:TAXI|BUS|UNDERGROUND*1..]-(d:DETEKTIV)) RETURN n" +
          ".number, m.number, d.number, length(p) ORDER BY m.number, d.number asc, length(p) desc");

      while (result.hasNext()) {
        wege.add(buildWeg(result.next(), "m.number", "d.number", "length(p)"));
      }
      tx.success();
    }
    LOG.debug("Alle Wege zum vom einem Nachbarn zu allen Detektiven");
    LOG.debug("{}", wege);

    Map<Position, Optional<Weg>> nachbar2KuerzesteDistanzZumNaechstenDetektiv = wege.stream()
        .collect(Collectors.groupingBy(Weg::getStart,
            Collectors.minBy(Comparator.comparing(Weg::getLaenge))));
    LOG.debug("Nachbar mit jeweils kürzester Distanz zu nächsten Detektiv");
    LOG.debug("{}", nachbar2KuerzesteDistanzZumNaechstenDetektiv);

    Optional<Weg> weg = nachbar2KuerzesteDistanzZumNaechstenDetektiv.values().stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.maxBy(Comparator.comparing(Weg::getLaenge)));
    LOG.debug("Größte Minimaldistanz zu allen Detektiven");
    LOG.debug("{}", weg.get().getLaenge());

    List<Weg> nachbarnMitGroessterDistanzZuAllenDetektiven =
        nachbar2KuerzesteDistanzZumNaechstenDetektiv.values()
            .stream()
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(weg1 -> weg1.getLaenge() == weg.get().getLaenge())
            .collect(Collectors.toList());
    LOG.debug("Nachbarn mit größter Distanz zu allen Detektiven");
    LOG.debug("{}", nachbarnMitGroessterDistanzZuAllenDetektiven);

    Map<Position, Double> avg = nachbarnMitGroessterDistanzZuAllenDetektiven.stream()
        .collect(Collectors.groupingBy(Weg::getStart, Collectors.averagingInt(Weg::getLaenge)));
    LOG.debug("Nachbar mit durchschnittlicher Distanz zu allen Detektiven");
    LOG.debug("{}", avg);

    Optional<Map.Entry<Position, Double>> optional = avg.entrySet().stream()
        .collect(Collectors.maxBy(Comparator.comparing(Map.Entry::getValue)));
    Position position = optional.get().getKey();
    LOG.debug("Nächste Position");
    LOG.debug("{}", position);

    return position;
  }

  private Weg buildWeg(final Map<String, Object> row, final String start, final String ende,
                       final String laenge) {
    return new Weg(new Position(Integer.parseInt(row.get(start).toString())),
        new Position(Integer.parseInt(row.get(ende).toString())),
        Integer.parseInt(row.get(laenge).toString()));
  }

  public void findeWegZuUndergroundInDreiZuegen() {
    final List<Weg> wege = new ArrayList<>();
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      Result result = graphDatabaseService.execute("MATCH p=(n:Node)-[:TAXI|BUS*3]-(m:Node) " +
          "RETURN n.number, m.number, length(p)");

      while (result.hasNext()) {
//        wege.add(buildWeg(result.next(), "n.number", "m.number", "3"));
      }
      tx.success();
    }
    LOG.debug("{}", wege);
  }

  public void aktualisiereSpielbrett(final Spiel spiel) {

  }

}
