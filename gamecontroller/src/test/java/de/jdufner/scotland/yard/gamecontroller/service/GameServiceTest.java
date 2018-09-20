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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.MrxService;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.position.StartPosition;
import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
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
public class GameServiceTest {

  @Spy
  @InjectMocks
  private GameService gameService;

  @Mock
  private StartPositionService startPositionService;
  @Mock
  private StartTicketService startTicketService;
  @Mock
  private MrxService mrxService;
  @Mock
  private DetectiveService detectiveService;

  @Test
  public void whenInitializeGame_expectStartPostitionIsSet() {
    // arrange
    when(startPositionService.zieheFreieStartPosition()).thenReturn(mock(StartPosition.class));

    // act
    Game game = gameService.initializeGame();

    // assert
    verify(startPositionService, times(5)).zieheFreieStartPosition();
    verify(mrxService).initialize(any(), any());
    verify(detectiveService, times(4)).initialize(any(), any());
  }

  @Test
  public void whenInitializeGame_expectTicketsAreSet() {
    // arrange
    Tickets mrxTickets = new Tickets();
    when(startTicketService.getMrxTickets()).thenReturn(mrxTickets);
    Tickets detectiveTickets = new Tickets();
    when(startTicketService.getDetectiveTickets()).thenReturn(detectiveTickets);

    // act
    gameService.initializeGame();

    // assert
    verify(startTicketService).getMrxTickets();
    verify(startTicketService, times(4)).getDetectiveTickets();
    verify(mrxService).initialize(null, mrxTickets);
    verify(detectiveService, times(4)).initialize(null, detectiveTickets);
  }

}
