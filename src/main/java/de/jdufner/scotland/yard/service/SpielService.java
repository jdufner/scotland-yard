package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.spiel.Weg;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

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

  public void displaySpieler(final Collection<Spieler> spielers) {
    spielers.stream().forEach(spieler -> {
      try (final Transaction tx = graphDatabaseService.beginTx()) {
        graphDatabaseService.execute("MATCH (n:Node) WHERE n.number = " + spieler.letztePosition
            ().getPosition() + " SET n:" + spieler.name().toUpperCase());
        tx.success();
      }
    });
  }

  public void ermittleKuerzesteDistanzenZwischenJeweilsAllenKnoten() {
    try (final Transaction tx = graphDatabaseService.beginTx()) {
      for (int n = 1; n <= 198; n++) {
        for (int m = n + 1; m <= 199; m++) {
          Result result = graphDatabaseService.execute("MATCH p=shortestPath((n:Node)-" +
              "[:TAXI|BUS|UNDERGROUND*1..10]-(m:Node)) WHERE n.number = " + n + " AND m.number = " +
              m + " RETURN DISTINCT n.number, m.number, length(p)");
          if (result.hasNext()) {
            final Map<String, Object> row = result.next();
            final Weg weg = new Weg(new Position(Integer.parseInt(row.get("n.number").toString())),
                new Position(Integer.parseInt(row.get("m.number").toString())),
                Integer.parseInt(row.get("length(p)").toString()));
          }
        }
      }
      tx.close();
    }
  }

}
