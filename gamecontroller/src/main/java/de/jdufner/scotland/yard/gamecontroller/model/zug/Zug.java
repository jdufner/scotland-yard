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

package de.jdufner.scotland.yard.gamecontroller.model.zug;

import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.Ticket;

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
