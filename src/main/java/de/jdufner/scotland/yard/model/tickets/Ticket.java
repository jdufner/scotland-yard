package de.jdufner.scotland.yard.model.tickets;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class Ticket {

  private int anzahl;

  Ticket(final int anzahl) {
    this.anzahl = anzahl;
  }

  public int getAnzahl() {
    return anzahl;
  }

}
