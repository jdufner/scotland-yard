package de.jdufner.scotland.yard.model.spiel;

import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Spiel {

  private static final Logger LOG = LoggerFactory.getLogger(Spiel.class);

  private final MrX mrX;
  private final List<Detektiv> detektivs;
  private final List<Spieler> spielers = new ArrayList<>();

  private boolean spielBeendet = false;

  public Spiel(final MrX mrX, final List<Detektiv> detektivs) {
    this.mrX = mrX;
    this.detektivs = detektivs;
    spielers.add(mrX);
    spielers.addAll(detektivs);
  }

  public List<Spieler> getSpieler() {
    return spielers;
  }

  public void spieleRunden() {
    IntStream.rangeClosed(1, 21).anyMatch(i -> zieheSpieler(i));
    beendeSpiel("Mr.X hat gewonnen!");
  }

  private boolean zieheSpieler(final int runde) {
    LOG.info("Runde: " + runde);
    return spielers.stream().anyMatch(spieler -> {
      spieler.ziehe();
      if (habenDetektiveMrXGefangen()) {
        beendeSpiel("Detektive haben gewonnen!");
        return true;
      }
      return false;
    });
  }

  private boolean habenDetektiveMrXGefangen() {
    for (Detektiv detektiv : detektivs) {
      if (detektiv.letztePosition().equals(mrX.letztePosition())) {
        return true;
      }
    }
    return false;
  }

  private void beendeSpiel(final String text) {
    if (spielBeendet) {
      return;
    }
    LOG.info(text);
    spielBeendet = true;
  }

  @Override
  public String toString() {
    return "Spieler: {" + spielers.toString() + "}";
  }

}
