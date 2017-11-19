package de.jdufner.scotland.yard.model.spiel;

import de.jdufner.scotland.yard.model.position.Position;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Weg {

  private final Position start;
  private final Position ende;
  private final int laenge;

  public Weg(final Position start, final Position ende, final int laenge) {
    this.start = start;
    this.ende = ende;
    this.laenge = laenge;
  }

  @Override
  public String toString() {
    return "Start: {" + start + "}, Ende: {" + ende + "}, Länge: " + laenge;
  }
}
