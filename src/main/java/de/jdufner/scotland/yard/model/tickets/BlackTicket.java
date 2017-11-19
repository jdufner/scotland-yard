package de.jdufner.scotland.yard.model.tickets;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class BlackTicket extends Ticket {

  public BlackTicket(final int anzahl) {
    super(anzahl);
  }

  @Override
  public String toString() {
    return "BlackTicket: " + getAnzahl();
  }

}
