package de.jdufner.scotland.yard.model.spiel;

import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Spiel {

  private static final Logger LOG = LoggerFactory.getLogger(Spiel.class);

  private final List<Spieler> spielers = new ArrayList<>();
  private final MrX mrX;
  private final List<Detektiv> detektivs = new ArrayList<>();

  public Spiel() {
    mrX = new MrX(Startposition.zieheFreieStartposition());
    spielers.add(mrX);
    detektivs.add(new Detektiv(Startposition.zieheFreieStartposition()));
    detektivs.add(new Detektiv(Startposition.zieheFreieStartposition()));
    detektivs.add(new Detektiv(Startposition.zieheFreieStartposition()));
    detektivs.add(new Detektiv(Startposition.zieheFreieStartposition()));
    spielers.addAll(detektivs);
  }

  public List<Spieler> getSpieler() {
    return spielers;
  }

  public void spieleRunden() {
    int anzahlRunden = 22;
    for (int runde = 1; runde <= anzahlRunden; runde++) {
      zieheSpieler();
    }
  }

  private void zieheSpieler() {
    for (final Spieler spieler : spielers) {
      spieler.macheNaechstenZug();
      if (habenDetektiveMrXGefangen()) {
        beendeSpiel("Detektive haben gewonnen!");
        return;
      }
    }
    beendeSpiel("Mr.X hat gewonnen!");
  }

  private boolean habenDetektiveMrXGefangen() {
    return false;
  }

  private void beendeSpiel(final String text) {
    LOG.info(text);
  }

  @Override
  public String toString() {
    return "Spieler: {" + spielers.toString() + "}";
  }

}
