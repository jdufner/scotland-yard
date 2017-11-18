package de.jdufner.scotland.yard.model.spieler;

import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.tickets.Ticket;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class Spieler {

  final Startposition startposition;
  final Collection<Ticket> tickets = new HashSet<>();

  Spieler(final Startposition startposition) {
    this.startposition = startposition;
  }

}
