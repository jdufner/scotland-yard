package de.jdufner.scotland.yard.model.tickets;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Underground extends Ticket {

  public Underground(final int anzahl) {
    super(anzahl);
  }

  @Override
  public String toString() {
    return "Underground: " + getAnzahl();
  }

}
