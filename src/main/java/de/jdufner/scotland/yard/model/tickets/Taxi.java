package de.jdufner.scotland.yard.model.tickets;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Taxi extends Ticket {

  public Taxi(final int anzahl) {
    super(anzahl);
  }

  @Override
  public String toString() {
    return "Taxi: " + getAnzahl();
  }

}
