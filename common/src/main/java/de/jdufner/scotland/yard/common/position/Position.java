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

package de.jdufner.scotland.yard.common.position;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Position {

  private int position;

  private Position() {
  }

  public Position(int position) {
    this.position = position;
  }

  public int getPosition() {
    return position;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Position that = (Position) o;
    return position == that.position;
  }

  @Override
  public int hashCode() {
    return position;
  }

  @Override
  public String toString() {
    return "Position: " + String.valueOf(position);
  }

  public String asNodeAttribute() {
    return "{number:" + position + "}";
  }

}
