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
import de.jdufner.scotland.yard.gamecontroller.model.zug.Zug;

import java.util.ArrayList;
import java.util.List;

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

  @Override
  public void ziehe(final Zug zug) {
    zieheAuf(zug.getZiel());
    consumeTicket(zug.getTicket());
  }

  public static class Builder {

    private List<Detective> detectives = new ArrayList<>();

    public Builder(final int... startpositionAsInts) {
      int nummer = 0;
      for (final int startpositionAsInt : startpositionAsInts) {
        detectives.add(new Detective(nummer++, new PlayerInfo(PlayerInfo.Type.DETECTIVE, new PlayerInfo.Id("1")), new StartPosition(startpositionAsInt), new Tickets()));
      }
    }

    public List<Detective> build() {
      return detectives;
    }

  }

  private class Nummer {
    private final int value;

    private Nummer(final int value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "Nummer: " + value;
    }
  }

}
