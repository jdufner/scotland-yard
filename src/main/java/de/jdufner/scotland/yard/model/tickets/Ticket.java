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

  public void verbrauche(final Ticket ticket) {
    if (this.getClass().equals(ticket.getClass())) {
      anzahl -= ticket.anzahl;
    }
  }

}
