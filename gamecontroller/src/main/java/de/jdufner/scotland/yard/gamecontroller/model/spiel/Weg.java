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

package de.jdufner.scotland.yard.gamecontroller.model.spiel;

import de.jdufner.scotland.yard.common.position.Position;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Weg {

  private final Position start;
  private final Position ende;
  private final int laenge;

  public Weg(final Position start, final Position ende, final int laenge) {
    this.start = start;
    this.ende = ende;
    this.laenge = laenge;
  }

  public Position getStart() {
    return start;
  }

  public Position getEnde() {
    return ende;
  }

  public int getLaenge() {
    return laenge;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Weg weg = (Weg) o;

    if (laenge != weg.laenge) return false;
    if (!start.equals(weg.start)) return false;
    return ende.equals(weg.ende);
  }

  @Override
  public int hashCode() {
    int result = start.hashCode();
    result = 31 * result + ende.hashCode();
    result = 31 * result + laenge;
    return result;
  }

  @Override
  public String toString() {
    return "Start: {" + start + "}, Ende: {" + ende + "}, Länge: " + laenge;
  }

}
