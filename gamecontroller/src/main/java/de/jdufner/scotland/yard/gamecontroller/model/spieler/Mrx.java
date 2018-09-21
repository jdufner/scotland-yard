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

package de.jdufner.scotland.yard.gamecontroller.model.spieler;

import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.position.StartPosition;
import de.jdufner.scotland.yard.gamecontroller.model.zug.Zug;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Mrx extends Player {

  // TODO [jdufner, 2018-09-19] Is this the right place?
  private static final int[] auftauchen = {3, 8, 13, 18};

  public Mrx(final StartPosition startPosition, final Tickets tickets) {
    super(startPosition, tickets);
  }

  @Override
  public String toString() {
    return "Mr. X: " + super.toString();
  }

  @Override
  public void ziehe(final Zug zug) {
    zieheAuf(zug.getZiel());
    consumeTicket(zug.getTicket());
  }

  public static class Builder {

    private Mrx mrX;

    public Builder(final int startpositionAsInt) {
      mrX = new Mrx(new StartPosition(startpositionAsInt), new Tickets());
    }

    public Mrx build() {
      return mrX;
    }

  }

}