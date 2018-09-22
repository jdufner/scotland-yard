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
 */

package de.jdufner.scotland.yard.gamecontroller.model.spieler;

import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.position.StartPosition;
import de.jdufner.scotland.yard.common.ticket.Ticket;
import de.jdufner.scotland.yard.gamecontroller.model.zug.Zug;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class Player {

  final PlayerInfo playerInfo;
  final StartPosition startPosition;
  final Tickets tickets;
  final List<Position> track = new ArrayList();

  Player(final PlayerInfo playerInfo, final StartPosition startPosition, final Tickets tickets) {
    this.playerInfo = playerInfo;
    this.startPosition = startPosition;
    this.tickets = tickets;
    track.add(startPosition);
  }

  @Override
  public String toString() {
    return "{" + getCurrentPosition() +
        ", Tickets: " + tickets + "}";
  }

  public String name() {
    return getClass().getSimpleName().toUpperCase();
  }

  public Position getCurrentPosition() {
    return track.get(track.size() - 1);
  }

  public List<Position> getTrack() {
    return track;
  }

  public abstract void ziehe(final Zug zug);

  public void zieheAuf(final Position naechstePosition) {
    track.add(naechstePosition);
  }

  public void consumeTicket(final Ticket verbrauchteTickets) {
//    tickets.stream()
//        //.filter(ticket -> ticket.getClass().equals(verbrauchteTickets.getClass()))
//        .forEach(ticket -> ticket.consume());
  }

  public Tickets getTickets() {
    return tickets;
  }
}
