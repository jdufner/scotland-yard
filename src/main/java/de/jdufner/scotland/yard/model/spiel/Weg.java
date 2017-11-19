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

  public Position getStart() {
    return start;
  }

  public Position getEnde() {
    return ende;
  }

  public int getLaenge() {
    return laenge;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Weg weg = (Weg) o;

    if (laenge != weg.laenge) return false;
    if (!start.equals(weg.start)) return false;
    return ende.equals(weg.ende);
  }

  @Override
  public int hashCode() {
    int result = start.hashCode();
    result = 31 * result + ende.hashCode();
    result = 31 * result + laenge;
    return result;
  }

  @Override
  public String toString() {
    return "Start: {" + start + "}, Ende: {" + ende + "}, Länge: " + laenge;
  }

}
