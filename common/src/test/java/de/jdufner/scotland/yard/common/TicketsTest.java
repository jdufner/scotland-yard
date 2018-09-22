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

import de.jdufner.scotland.yard.common.ticket.BusTicket;
import de.jdufner.scotland.yard.common.ticket.DoppelzugTicket;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import de.jdufner.scotland.yard.common.ticket.Underground;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class TicketsTest {

  @Test
  public void whenTicketsContainsMoreTaxiTicketThanRequested_expectTrue() {
    // arrange
    Tickets tickets = new Tickets();
    TaxiTicket taxiTicket = new TaxiTicket(3);
    tickets.add(taxiTicket);

    // act + assert
    assertTrue(tickets.contains(new TaxiTicket(1)));
  }

  @Test
  public void whenTicketsContainsLessTaxiTicketsThenRequested_expectFalse() {
    // arrange
    Tickets tickets = new Tickets();
    TaxiTicket taxiTicket = new TaxiTicket(3);
    tickets.add(taxiTicket);

    // act + assert
    assertFalse(tickets.contains(new TaxiTicket(5)));
  }

  @Test
  public void whenTicketsContainsOneTaxiTicketButRequestBusTicket_expectFalse() {
    // arrange
    Tickets tickets = new Tickets();
    TaxiTicket taxiTicket = new TaxiTicket(3);
    tickets.add(taxiTicket);

    // act + assert
    assertFalse(tickets.contains(new BusTicket(1)));
  }

  @Test
  public void whenTicketsContainsDoppelzugAndBusAndTaxtTicket_expectTrue() {
    // arrange
    Tickets tickets = new Tickets();
    DoppelzugTicket doppelzugTicket = new DoppelzugTicket(1);
    tickets.add(doppelzugTicket);
    TaxiTicket taxiTicket = new TaxiTicket(1);
    tickets.add(taxiTicket);
    BusTicket busTicket = new BusTicket(1);
    tickets.add(busTicket);

    DoppelzugTicket searchTicket = new DoppelzugTicket(1);
    searchTicket.setFirstTicket(new BusTicket(1));
    searchTicket.setSecondTicket(new TaxiTicket(1));

    // act + assert
    assertTrue(tickets.contains(searchTicket));
  }

  @Test
  public void whenTicketsContainsDoppelzugAndBusAndUndergroudTicket_expectFalse() {
    // arrange
    Tickets tickets = new Tickets();
    DoppelzugTicket doppelzugTicket = new DoppelzugTicket(1);
    tickets.add(doppelzugTicket);
    Underground underground = new Underground(1);
    tickets.add(underground);
    BusTicket busTicket = new BusTicket(1);
    tickets.add(busTicket);

    DoppelzugTicket searchTicket = new DoppelzugTicket(1);
    searchTicket.setFirstTicket(new BusTicket(1));
    searchTicket.setSecondTicket(new TaxiTicket(1));

    // act + assert
    assertFalse(tickets.contains(searchTicket));
  }

  @Test
  public void whenTicketsContainsBusAndTaxtTicket_expectFalse() {
    // arrange
    Tickets tickets = new Tickets();
    TaxiTicket taxiTicket = new TaxiTicket(1);
    tickets.add(taxiTicket);
    BusTicket busTicket = new BusTicket(1);
    tickets.add(busTicket);

    DoppelzugTicket searchTicket = new DoppelzugTicket(1);
    searchTicket.setFirstTicket(new BusTicket(1));
    searchTicket.setSecondTicket(new TaxiTicket(1));

    // act + assert
    assertFalse(tickets.contains(searchTicket));
  }

}