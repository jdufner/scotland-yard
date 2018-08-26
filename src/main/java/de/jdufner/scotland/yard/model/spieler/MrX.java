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

import de.jdufner.scotland.yard.model.position.StartPosition;
import de.jdufner.scotland.yard.common.BlackTicket;
import de.jdufner.scotland.yard.common.Bus;
import de.jdufner.scotland.yard.model.tickets.Doppelzug;
import de.jdufner.scotland.yard.common.Taxi;
import de.jdufner.scotland.yard.common.Underground;
import de.jdufner.scotland.yard.model.zug.Zug;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class MrX extends Spieler {

  private static final int[] auftauchen = {3, 8, 13, 18};

  public MrX(final StartPosition startPosition) {
    super(startPosition);
    tickets.add(new Taxi(4));
    tickets.add(new Bus(3));
    tickets.add(new Underground(3));
    tickets.add(new Doppelzug(2));
    tickets.add(new BlackTicket(2));
  }

  @Override
  public String toString() {
    return "Mr. X: " + super.toString();
  }

  @Override
  public void ziehe(final Zug zug) {
    zieheAuf(zug.getZiel());
    verbraucheTickets(zug.getTicket());
  }

  public static class Builder {

    private MrX mrX;

    public Builder(final int startpositionAsInt) {
      mrX = new MrX(new StartPosition(startpositionAsInt));
    }

    public MrX build() {
      return mrX;
    }

  }

}
