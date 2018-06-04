/*
 * Scotland Yard is a simulation of the board game.
 * Copyright (C) 2008-2018  Juergen Dufner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.jdufner.scotland.yard.model.spieler;

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.tickets.Ticket;
import de.jdufner.scotland.yard.model.zug.Zug;
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
    return getClass().getSimpleName().toUpperCase();
  }

  public Position letztePosition() {
    return positions.get(positions.size() - 1);
  }

  public List<Position> getPositions() {
    return positions;
  }

  public abstract void ziehe(final Zug zug);

  public void zieheAuf(final Position naechstePosition) {
    positions.add(naechstePosition);
  }

  protected void verbraucheTickets(final Ticket verbrauchteTickets) {
    tickets.stream()
        //.filter(ticket -> ticket.getClass().equals(verbrauchteTickets.getClass()))
        .forEach(ticket -> ticket.verbrauche(ticket));
  }
}
