package de.jdufner.scotland.yard.model.tickets;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Bus extends Ticket {

  public Bus(final int anzahl) {
    super(anzahl);
  }

  @Override
  public String toString() {
    return "Bus: " + getAnzahl();
  }

}
