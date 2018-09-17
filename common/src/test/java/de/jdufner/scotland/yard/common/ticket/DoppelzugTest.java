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

import org.assertj.core.api.SoftAssertions;
import org.junit.Test;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class DoppelzugTest {

  @Test
  public void whenDoubleRounds_expectThreeTicketsConsumed() {
    // arrange
    Doppelzug doppelzugTickets = new Doppelzug(2);
    Taxi firstTicket = new Taxi(1);
    Taxi secondTicket = new Taxi(1);

    // act
    Doppelzug doppelzug = doppelzugTickets.consume(firstTicket, secondTicket);

    // assert
    SoftAssertions.assertSoftly(softAssertions -> {
      softAssertions.assertThat(doppelzug.getAnzahl()).isEqualTo(1);
      softAssertions.assertThat(doppelzugTickets.getAnzahl()).isEqualTo(1);
      softAssertions.assertThat(doppelzug.getFirstTicket()).isEqualTo(firstTicket);
      softAssertions.assertThat(doppelzug.getSecondTicket()).isEqualTo(secondTicket);
    });
  }

  @Test(expected = RuntimeException.class)
  public void whenDoubleRoundsConsumed_expectException() {
    // arrange
    Doppelzug doppelzugTickets = new Doppelzug(0);
    Taxi firstTicket = new Taxi(1);
    Taxi secondTicket = new Taxi(1);

    // act + assert
    doppelzugTickets.consume(firstTicket, secondTicket);
  }

}