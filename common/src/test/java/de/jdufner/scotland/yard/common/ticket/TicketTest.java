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

package de.jdufner.scotland.yard.common.ticket;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class TicketTest {

  @Test
  public void whenConsumeTicket_expectExactlyOneTicketLesserThanBefore() {
    // arrange
    int numberOfTickets = 10;
    Ticket ticket = new TestTicket(numberOfTickets);

    // act
    ticket.consume(ticket);

    // assert
    Assertions.assertThat(ticket.getAnzahl()).isEqualTo(numberOfTickets - 1);
  }

  @Test
  public void whenCompareDifferentTicketTypes_expectDifferent() {
    // arrange
    int numberOfTickets = 10;
    Ticket taxi = new Taxi(numberOfTickets);
    Ticket bus = new Bus(numberOfTickets);

    // act + assert
    Assertions.assertThat(taxi).isNotEqualTo(bus);
  }

  private class TestTicket extends Ticket {
    public TestTicket(int anzahl) {
      super(anzahl);
    }
  }

}