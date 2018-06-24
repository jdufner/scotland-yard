package de.jdufner.scotland.yard.model.zug;

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.tickets.Ticket;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Zug {

  private Position ziel;
  private Ticket ticket;

  Zug() {
  }

  public Zug(final Position ziel, final Ticket ticket) {
    this.ziel = ziel;
    this.ticket = ticket;
  }

  public Position getZiel() {
    return ziel;
  }

  public Ticket getTicket() {
    return ticket;
  }
}
