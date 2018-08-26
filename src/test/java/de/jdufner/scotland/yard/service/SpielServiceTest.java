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

package de.jdufner.scotland.yard.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.MrxService;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.model.position.StartPosition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class SpielServiceTest {

  @Spy
  @InjectMocks
  private SpielService spielService;

  @Mock
  private StartPositionService startPositionService;
  @Mock
  private StartTicketService startTicketService;
  @Mock
  private MrxService mrxService;
  @Mock
  private DetectiveService detectiveService;

  @Test
  public void whenErzeugeMrx_expectStartPostitionWirdGesetzt() {
    // arrange
    StartPosition startPosition = new StartPosition(1);
    when(startPositionService.zieheFreieStartPosition()).thenReturn(startPosition);

    // act
    spielService.erzeugeSpiel();

    // assert
    verify(mrxService).initialize(startPosition, null);
  }

  @Test
  public void whenErzeugeMrx_expectTicketsWerdenGesetzt() {
    // arrange
    Tickets tickets = new Tickets();
    when(startTicketService.getMrxTickets()).thenReturn(tickets);

    // act
    spielService.erzeugeSpiel();

    // assert
    verify(mrxService).initialize(null, tickets);
  }

}
