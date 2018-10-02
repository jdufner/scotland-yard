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

package de.jdufner.scotland.yard.common;

import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.Ticket;

/**
 * This interface has to be implemented by the game logic, that represents Mr X.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
public interface MrxService {

  // tag::initializeMethod[]
  /**
   * Sets Mr.X to the start position and gives him the tickets to use the public traffic.
   *
   * This method is called by the game controller.
   *
   * @param position
   * @param tickets
   */
  void initialize(final PlayerInfo playerInfo, final Position position, final Tickets tickets); // <1>
  // end::initializeMethod[]

  /**
   * Asks Mr. X. to do the next move.
   *
   * This method is called by the game controller.
   */
  Move nextMove();

  /**
   * Gives Mr. X a Ticket.
   * <p>
   * Usually after a detective has moved he gives the consumed ticket to Mr. X.
   *
   * @param ticket The consumed ticket of a detective is a new ticket for Mr. X.
   */
  void giveTicket(Ticket ticket);

  /**
   * Informs Mr. X about the current position of a detective.
   *
   * @param playerInfo The ID of an detective.
   * @param position   The current position of an detective.
   */
  void setDetectivesPosition(PlayerInfo playerInfo, Position position);

}
