package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class DetektivService extends SpielerService<Detektiv> {

  private static final Logger LOG = LoggerFactory.getLogger(DetektivService.class);

  public DetektivService(final SpielbrettService spielbrettService) {
    super(spielbrettService);
  }

  @Override
  protected PositionUndTickets ermittleNächstenZug(final Spiel spiel, final Detektiv spieler) {
//    if (spiel.getAktuelleRunde() < 3) {
//      spielbrettService.findeWegZuUndergroundInAnzahlZuegen(spieler, 3 - spiel.getAktuelleRunde());
//    }

    return new PositionUndTickets(spielbrettService.findeWegZuUndergroundInAnzahlZuegen(spieler,
        3 - spiel.getAktuelleRunde()), null);

//    return new PositionUndTickets(spieler.letztePosition(), null);
  }

}
