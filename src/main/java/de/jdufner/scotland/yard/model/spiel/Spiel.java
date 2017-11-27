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
 * Das Spiel hält Status der Figuren, der Runden etc. Die Transitionen werden von einem Service
 * ausgelöst.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Spiel {

  private static final Logger LOG = LoggerFactory.getLogger(Spiel.class);

  private static final int MAX_ANZAHL_RUNDEN = 21;

  private final MrX mrX;
  private final List<Detektiv> detektivs;
  private final List<Spieler> spielers = new ArrayList<>();

  private int aktuelleRunde;

  private boolean beendet = false;

  public Spiel(final MrX mrX, final List<Detektiv> detektivs) {
    this.mrX = mrX;
    this.detektivs = detektivs;
    spielers.add(mrX);
    spielers.addAll(detektivs);
  }

  public List<Spieler> getSpieler() {
    return spielers;
  }

  public int naechsteRunde() {
    return aktuelleRunde++;
  }

  public int getAktuelleRunde() {
    return aktuelleRunde;
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

  private void beendeSpiel(final String text) {
    if (beendet) {
      return;
    }
    LOG.info(text);
    beendet = true;
  }

  private boolean habenDetektiveMrXGefangen() {
    for (Detektiv detektiv : detektivs) {
      if (detektiv.letztePosition().equals(mrX.letztePosition())) {
        return true;
      }
    }
    return false;
  }

  public boolean isBeendet() {
    return sindAlleRundenGespiel() || habenDetektiveMrXGefangen();
  }

  private boolean sindAlleRundenGespiel() {
    return aktuelleRunde >= MAX_ANZAHL_RUNDEN;
  }

  @Override
  public String toString() {
    return "Spieler: {" + spielers.toString() + "}";
  }

}
