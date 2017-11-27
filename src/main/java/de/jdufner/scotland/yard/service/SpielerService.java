package de.jdufner.scotland.yard.service;

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

  public abstract void ziehe(final T spieler);

}
