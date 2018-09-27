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
import de.jdufner.scotland.yard.common.position.StartPosition;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Detective extends Player {

  private final Nummer nummer;

  public Detective(final int nummer, final PlayerInfo playerInfo, final StartPosition startPosition, final Tickets tickets) {
    super(playerInfo, startPosition, tickets);
    this.nummer = new Nummer(nummer);
  }

  @Override
  public String toString() {
    return "Detective: " + super.toString() + ", " + nummer;
  }

  private static class Nummer {
    private final int value;

    private Nummer(final int value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "Nummer: " + value;
    }
  }

  public static class Builder {


    public static Builder defaultDetective() {
      return new Builder()
          .withNummer(1)
          .withPlayerInfo(PlayerInfo.Builder.newDetective().build())
          .withStartpositionAsInt(26)
          .withTickets(Tickets.Builder.defaultDetectiveTickets().build());
    }

    private Nummer nummer;
    private StartPosition startPosition;
    private PlayerInfo playerInfo;
    private Tickets tickets;

    public Builder() {
    }

    public Builder withNummer(final int nummerAsInt) {
      this.nummer = new Nummer(nummerAsInt);
      return this;
    }

    public Builder withStartpositionAsInt(final int startpositionAsInt) {
      this.startPosition = new StartPosition(startpositionAsInt);
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

    public Detective build() {
      return new Detective(nummer.value, playerInfo, startPosition, tickets);
    }

  }

}
