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

package de.jdufner.scotland.yard.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Tickets {

  private final Collection<Ticket> tickets = new HashSet<Ticket>();

  public void add(Ticket ticket) {
    tickets.add(ticket);
  }

  public int getTaxiTickets() {
    return getTicketByType(Taxi.class);
  }

  private int getTicketByType(Class<? extends Ticket> aClass) {
    Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getClass().equals(aClass)).findFirst();
    return optionalTicket.isPresent() ? optionalTicket.get().getAnzahl() : 0;
  }

  public int getBusTickets() {
    return getTicketByType(Bus.class);
  }

  public int getUndergroundTickets() {
    return getTicketByType(Underground.class);
  }

  public int getBlackTickets() {
    return getTicketByType(BlackTicket.class);
  }
}
