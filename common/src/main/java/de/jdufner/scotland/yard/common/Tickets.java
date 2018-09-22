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

package de.jdufner.scotland.yard.common;

import de.jdufner.scotland.yard.common.ticket.BlackTicket;
import de.jdufner.scotland.yard.common.ticket.BusTicket;
import de.jdufner.scotland.yard.common.ticket.DoppelzugTicket;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import de.jdufner.scotland.yard.common.ticket.Ticket;
import de.jdufner.scotland.yard.common.ticket.Underground;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * This is a container for tickets during initialization of the service.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Tickets {

  private final List<Ticket> tickets = new LinkedList<>();

  public void add(Ticket ticket) {
    tickets.add(ticket);
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  private int getTicketByType(Class<? extends Ticket> aClass) {
    Optional<Ticket> optionalTicket = tickets.stream().filter(ticket -> ticket.getClass().equals(aClass)).findFirst();
    return optionalTicket.isPresent() ? optionalTicket.get().getAnzahl() : 0;
  }

  public int getTaxiTickets() {
    return getTicketByType(TaxiTicket.class);
  }

  public int getBusTickets() {
    return getTicketByType(BusTicket.class);
  }

  public int getUndergroundTickets() {
    return getTicketByType(Underground.class);
  }

  public int getBlackTickets() {
    return getTicketByType(BlackTicket.class);
  }

  public int getDoppelzugTickets() {
    return getTicketByType(DoppelzugTicket.class);
  }

  public boolean contains(final Ticket searchTicket) {
    return tickets.stream()
        .filter(ticket -> ticket.contains(searchTicket))
        .filter(ticket -> {
          if (searchTicket instanceof DoppelzugTicket) {
            return this.contains(((DoppelzugTicket) searchTicket).getFirstTicket())
                && this.contains(((DoppelzugTicket) searchTicket).getSecondTicket());
          }
          return true;
        })
        .anyMatch(ticket -> true);
  }

}
