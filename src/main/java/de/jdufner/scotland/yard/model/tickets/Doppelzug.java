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

package de.jdufner.scotland.yard.model.tickets;

import de.jdufner.scotland.yard.common.Ticket;

/**
 * Ist das überhaupt ein Ticket? Es ist die Erlaubnis in einer Rund zwei Züge machen zu dürfen,
 * aber es gibt kein Verkehrsmittel, das durch diese Klasse repräsentiert wird. Tendenziell fällt
 * diese Klasse wieder weg!
 *
 * @author Jürgen Dufner
 * @since 1.0
 * @deprecated Ist kein Verkehrsmittel, wird durch {@link de.jdufner.scotland.yard.model.zug.DoppelZug}
 *             ersetzt.
 */
@Deprecated
public class Doppelzug extends Ticket {

  public Doppelzug(final int anzahl) {
    super(anzahl);
  }

  @Override
  public String toString() {
    return "Doppelzug: " + getAnzahl();
  }

}
