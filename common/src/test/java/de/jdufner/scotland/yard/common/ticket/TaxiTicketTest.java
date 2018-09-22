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

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class TaxiTicketTest {

  @Test
  public void whenCompare_expectEquals() {
    // arrange
    int anzahl = 10;
    TaxiTicket taxiTicket1 = new TaxiTicket(anzahl);
    TaxiTicket taxiTicket2 = new TaxiTicket(anzahl);

    // act + assert
    Assertions.assertThat(taxiTicket1).isEqualTo(taxiTicket2);
  }

  @Test
  public void whenCompare_expectNotEquals() {
    // arrange
    TaxiTicket taxiTicket1 = new TaxiTicket(5);
    TaxiTicket taxiTicket2 = new TaxiTicket(10);

    // act + assert
    Assertions.assertThat(taxiTicket1).isNotEqualTo(taxiTicket2);
  }

}