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

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.tickets.BlackTicket;
import de.jdufner.scotland.yard.model.tickets.Bus;
import de.jdufner.scotland.yard.model.tickets.Doppelzug;
import de.jdufner.scotland.yard.model.tickets.Taxi;
import de.jdufner.scotland.yard.model.tickets.Underground;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class MrX extends Spieler {

  private static final int[] auftauchen = {3, 8, 13, 18};

  public MrX(final Startposition startposition) {
    super(startposition);
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
  public void zieheUndVerbraucheTickets(final PositionUndTickets positionUndTickets) {
    zieheAuf(positionUndTickets.getPosition());
    verbraucheTickets(positionUndTickets.getTickets());
  }

  public static class Builder {


    private MrX mrX;

    public Builder(final int startpositionAsInt) {
      mrX = new MrX(new Startposition(startpositionAsInt));
    }

    public MrX build() {
      return mrX;
    }

  }

}
