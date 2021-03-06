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

package de.jdufner.scotland.yard.gamecontroller.service;

import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.ticket.BlackTicket;
import de.jdufner.scotland.yard.common.ticket.BusTicket;
import de.jdufner.scotland.yard.common.ticket.DoppelzugTicket;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import de.jdufner.scotland.yard.common.ticket.UndergroundTicket;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class StartTicketServiceTest {

  private StartTicketService startTicketService = new StartTicketService();

  @Test
  public void whenGetMrxTickets_expectSpezificNumbersOfTickets() {
    // arrange

    // act
    Tickets mrxTickets = startTicketService.getMrxTickets();

    // assert
    assertThat(mrxTickets.getTickets()).containsExactly(new TaxiTicket(4), new BusTicket(3), new UndergroundTicket(3), new BlackTicket(2), new DoppelzugTicket(2));
  }

  @Test
  public void whenGetDetectiveTickets_expectSpezificNumbersOfTickets() {
    // arrange

    // act
    Tickets mrxTickets = startTicketService.getDetectiveTickets();

    // assert
    assertThat(mrxTickets.getTickets()).containsExactly(new TaxiTicket(10), new BusTicket(8), new UndergroundTicket(4));
  }

}