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

package de.jdufner.scotland.yard.common.ticket;

/**
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
public class DoppelzugTicket extends Ticket {

  private Ticket firstTicket;
  private Ticket secondTicket;

  public DoppelzugTicket(final int anzahl) {
    super(anzahl);
  }

//  @Override
//  public boolean contains(Ticket other) {
//    if (getClass() != other.getClass()) return false;
//    DoppelzugTicket that = (DoppelzugTicket) other;
//    return super.contains(that) && firstTicket.contains(that.firstTicket) && secondTicket.contains(that.secondTicket);
//  }

  public DoppelzugTicket consume(final Ticket firstTicket, final Ticket secondTicket) {
    if (firstTicket instanceof DoppelzugTicket || secondTicket instanceof DoppelzugTicket) {
      throw new RuntimeException();
    }
    this.firstTicket = firstTicket;
    this.secondTicket = secondTicket;
    consume();
    return this;
  }

  public Ticket getFirstTicket() {
    return firstTicket;
  }

  public void setFirstTicket(Ticket firstTicket) {
    this.firstTicket = firstTicket;
  }

  public Ticket getSecondTicket() {
    return secondTicket;
  }

  public void setSecondTicket(Ticket secondTicket) {
    this.secondTicket = secondTicket;
  }

  @Override
  public String toString() {
    return "DoppelzugTicket: " + getAnzahl();
  }

}
