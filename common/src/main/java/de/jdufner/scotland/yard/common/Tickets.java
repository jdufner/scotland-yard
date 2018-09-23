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

import de.jdufner.scotland.yard.common.ticket.DoppelzugTicket;
import de.jdufner.scotland.yard.common.ticket.Ticket;

import java.util.LinkedList;
import java.util.List;

/**
 * This is a container for tickets during initialization of the service.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Tickets {

  // TODO [jdufner, 2018-09-23] Is it better to use a Map?
  private final List<Ticket> tickets = new LinkedList<>();

  // TODO [jdufner, 2018-09-23] Replace by constructor parameter
  public void add(Ticket newTicket) {
    boolean present = tickets.stream()
        .filter(ticket -> ticket.getClass().equals(newTicket.getClass()))
        .map(ticket -> {
          ticket.add(newTicket);
          return ticket;
        })
        .findFirst().isPresent();
    if (!present) {
      tickets.add(newTicket);
    }
  }

  public List<Ticket> getTickets() {
    return tickets;
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
