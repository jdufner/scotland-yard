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

package de.jdufner.scotland.yard.common.move;

import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.Ticket;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Move {

  private final PlayerInfo playerInfo;
  private final Path path;

  public Move(final PlayerInfo playerInfo, final Position start, final Position end, final Ticket ticket) {
    this(playerInfo, new Path(start, end, ticket));
  }

  public Move(final PlayerInfo playerInfo, final Path path) {
    this.playerInfo = playerInfo;
    this.path = path;
  }

  public PlayerInfo getPlayerInfo() {
    return playerInfo;
  }

  public Position getStart() {
    return path.getStart();
  }

  public Position getEnd() {
    return path.getEnd();
  }

  public Ticket getTicket() {
    return path.getTicket();
  }

}
