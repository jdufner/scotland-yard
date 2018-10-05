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

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.MrxService;
import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class GameInitializationServiceTest {

  @Spy
  @InjectMocks
  private GameInitializationService gameInitializationService;

  @Mock
  private PlayerInfoService playerInfoService;
  @Mock
  private StartPositionService startPositionService;
  @Mock
  private StartTicketService startTicketService;
  @Mock
  private MrxService mrxService;
  @Mock
  private DetectiveService detectiveService;

  @Test
  public void whenInitializeGame_expectGameInitializedAndPlayerServicesCalled() {
    // arrange
    PlayerInfo mrxPlayerInfo = mock(PlayerInfo.class);
    when(playerInfoService.getMrxPlayerInfo()).thenReturn(mrxPlayerInfo);
    Tickets mrxTickets = mock(Tickets.class);
    when(startTicketService.getMrxTickets()).thenReturn(mrxTickets);

    PlayerInfo detectivePlayerInfo1 = mock(PlayerInfo.class);
    PlayerInfo detectivePlayerInfo2 = mock(PlayerInfo.class);
    PlayerInfo detectivePlayerInfo3 = mock(PlayerInfo.class);
    PlayerInfo detectivePlayerInfo4 = mock(PlayerInfo.class);
    when(playerInfoService.getDetectivePlayerInfo()).thenReturn(detectivePlayerInfo1, detectivePlayerInfo2, detectivePlayerInfo3, detectivePlayerInfo4);

    Tickets detectiveTickets1 = mock(Tickets.class);
    Tickets detectiveTickets2 = mock(Tickets.class);
    Tickets detectiveTickets3 = mock(Tickets.class);
    Tickets detectiveTickets4 = mock(Tickets.class);
    when(startTicketService.getDetectiveTickets()).thenReturn(detectiveTickets1, detectiveTickets2, detectiveTickets3, detectiveTickets4);

    Position mrxStartPosition = mock(Position.class);
    Position detectiveStartPosition1 = mock(Position.class);
    Position detectiveStartPosition2 = mock(Position.class);
    Position detectiveStartPosition3 = mock(Position.class);
    Position detectiveStartPosition4 = mock(Position.class);
    when(startPositionService.zieheFreieStartPosition()).thenReturn(mrxStartPosition, detectiveStartPosition1, detectiveStartPosition2, detectiveStartPosition3, detectiveStartPosition4);

    // act
    Game game = gameInitializationService.initializeGame();

    // assert
    verify(playerInfoService).getMrxPlayerInfo();
    verify(playerInfoService, times(4)).getDetectivePlayerInfo();
    verify(startTicketService).getMrxTickets();
    verify(startTicketService, times(4)).getDetectiveTickets();
    verify(startPositionService, times(5)).zieheFreieStartPosition();
    Assertions.assertThat(game.getPlayers().size()).isEqualTo(5);
    verify(mrxService).initialize(mrxPlayerInfo, mrxStartPosition, mrxTickets);
    InOrder inOrder = Mockito.inOrder(detectiveService, mrxService);
    inOrder.verify(detectiveService).initialize(detectivePlayerInfo1, detectiveStartPosition1, detectiveTickets1);
    inOrder.verify(mrxService).setDetectivesPosition(detectivePlayerInfo1, detectiveStartPosition1);
    inOrder.verify(detectiveService).initialize(detectivePlayerInfo2, detectiveStartPosition2, detectiveTickets2);
    inOrder.verify(mrxService).setDetectivesPosition(detectivePlayerInfo2, detectiveStartPosition2);
    inOrder.verify(detectiveService).initialize(detectivePlayerInfo3, detectiveStartPosition3, detectiveTickets3);
    inOrder.verify(mrxService).setDetectivesPosition(detectivePlayerInfo3, detectiveStartPosition3);
    inOrder.verify(detectiveService).initialize(detectivePlayerInfo4, detectiveStartPosition4, detectiveTickets4);
    inOrder.verify(mrxService).setDetectivesPosition(detectivePlayerInfo4, detectiveStartPosition4);
  }

}
