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

package de.jdufner.scotland.yard.gamecontroller.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.jdufner.scotland.yard.common.Tickets;
import org.junit.Test;

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
    assertThat(mrxTickets.getTaxiTickets()).isEqualTo(4);
    assertThat(mrxTickets.getBusTickets()).isEqualTo(3);
    assertThat(mrxTickets.getUndergroundTickets()).isEqualTo(3);
    assertThat(mrxTickets.getBlackTickets()).isEqualTo(2);
  }

  @Test
  public void whenGetDetectiveTickets_expectSpezificNumbersOfTickets() {
    // arrange

    // act
    Tickets mrxTickets = startTicketService.getDetectiveTickets();

    // assert
    assertThat(mrxTickets.getTaxiTickets()).isEqualTo(10);
    assertThat(mrxTickets.getBusTickets()).isEqualTo(8);
    assertThat(mrxTickets.getUndergroundTickets()).isEqualTo(4);
    assertThat(mrxTickets.getBlackTickets()).isEqualTo(0);
  }

}