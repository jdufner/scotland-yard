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
import de.jdufner.scotland.yard.common.ticket.Ticket;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Mrx extends Player {

  public Mrx(final PlayerInfo playerInfo, final Position startPosition, final Tickets tickets) {
    super(playerInfo, startPosition, tickets);
  }

  @Override
  public String toString() {
    return "Mr. X: " + super.toString();
  }

  public void addTicket(Ticket ticket) {
    tickets.add(ticket);
  }

  public static class Builder {

    public static Builder defaultMrx() {
      return new Builder()
          .withPlayerInfo(PlayerInfo.Builder.newMrx().build())
          .withStartpositionAsInt(13)
          .withTickets(Tickets.Builder.defaultMrxTickets().build());
    }

    private Position startPosition;
    private PlayerInfo playerInfo;
    private Tickets tickets;

    public Builder() {
    }

    public Builder withStartpositionAsInt(final int startpositionAsInt) {
      this.startPosition = new Position(startpositionAsInt);
      return this;
    }

    public Builder withPlayerInfo(final PlayerInfo playerInfo) {
      this.playerInfo = playerInfo;
      return this;
    }

    public Builder withTickets(final Tickets tickets) {
      this.tickets = tickets;
      return this;
    }

    public Mrx build() {
      return new Mrx(playerInfo, startPosition, tickets);
    }

  }

}
