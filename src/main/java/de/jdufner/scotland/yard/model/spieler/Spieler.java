package de.jdufner.scotland.yard.model.spieler;

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.tickets.Ticket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class Spieler {

  final Startposition startposition;
  final Collection<Ticket> tickets = new HashSet<>();
  final List<Position> positions = new ArrayList();

  Spieler(final Startposition startposition) {
    this.startposition = startposition;
    positions.add(startposition);
  }

  @Override
  public String toString() {
    return "{" + letztePosition() +
        ", Tickets: " + tickets + "}";
  }

  public String name() {
    return getClass().getSimpleName();
  }

  public Position letztePosition() {
    return positions.get(positions.size() - 1);
  }

  public abstract void ziehe();

  public abstract void macheNaechstenZug();
}
