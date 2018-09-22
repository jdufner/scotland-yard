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
 * Superklasse für alle Ticket: TaxiTicket, BusTicket, Underground und Blackticket.
 * <p>
 * Soll die Anzahl der Tickets durch Instanzen dargestellt werden oder durch einen Zähler? Im
 * Moment entscheide ich mich mal für einen Zähler. Mal schauen wir gut das klappt.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class Ticket {

  private int anzahl;

  public Ticket(final int anzahl) {
    this.anzahl = anzahl;
  }

  public int getAnzahl() {
    return anzahl;
  }

  public boolean contains(Ticket other) {
    if (getClass() != other.getClass()) return false;
    return anzahl >= other.anzahl;
  }

  public void consume() {
    if (anzahl <= 0) {
      throw new RuntimeException();
    }
    --anzahl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Ticket ticket = (Ticket) o;
    return anzahl == ticket.anzahl;
  }

  @Override
  public int hashCode() {
    return anzahl;
  }
}
