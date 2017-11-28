package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.spieler.Spieler;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class SpielerService<T extends Spieler> {

  protected final SpielbrettService spielbrettService;

  public SpielerService(final SpielbrettService spielbrettService) {
    this.spielbrettService = spielbrettService;
  }

  public abstract Class<T> getSpielerType();

  public abstract Position ziehe(final T spieler);

  /**
   * Template-Method für die Durchführung eines Zuges.
   *
   * @param spieler
   */
  public void fuehreZugDurch(final T spieler) {
    PositionUndTickets positionUndTickets = ermittleNächstenZug();
    spieler.zieheUndVerbraucheTickets(positionUndTickets);
  }

  protected abstract PositionUndTickets ermittleNächstenZug();

}
