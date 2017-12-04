package de.jdufner.scotland.yard.model;

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.tickets.Ticket;

import java.util.List;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class PositionUndTickets {

  final Position position;
  final List<Ticket> tickets;

  public PositionUndTickets(final Position position, final List<Ticket> tickets) {
    this.position = position;
    this.tickets = tickets;
  }

  public Position getPosition() {
    return position;
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

}
