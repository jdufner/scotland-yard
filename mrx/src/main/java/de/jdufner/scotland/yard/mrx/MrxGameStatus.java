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

package de.jdufner.scotland.yard.mrx;

import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.Ticket;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class MrxGameStatus {

  private final PlayerInfo playerInfo;
  private final List<Position> track = new LinkedList<>();
  private final Tickets tickets;
  private final Map<PlayerInfo, Collection<Position>> detectivesPosition = new HashMap<>();

  MrxGameStatus(final PlayerInfo playerInfo, final Position position, final Tickets tickets) {
    this.playerInfo = playerInfo;
    this.track.add(position);
    this.tickets = tickets;
  }

  void giveTicket(final Ticket ticket) {
    tickets.add(ticket);
  }

  void setDetectivesPosition(final PlayerInfo playerInfo, final Position position) {
    detectivesPosition.computeIfAbsent(playerInfo, playerInfo1 -> new LinkedList<>()).add(position);
  }

  Position getCurrentPosition() {
    return track.get(track.size() - 1);
  }

  Tickets getTickets() {
    return tickets;
  }

  public void doMove(Move move) {
    track.add(move.getEnd());
  }

}
