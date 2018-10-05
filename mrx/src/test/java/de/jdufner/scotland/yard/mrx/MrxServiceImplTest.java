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

package de.jdufner.scotland.yard.mrx;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class MrxServiceImplTest {

  @InjectMocks
  private MrxServiceImpl mrxService;

  @Mock
  private MrxMove mrxMove;

  @Test
  public void testInitialize_whenCalled_expectGameStatusCreated() {
    // arrange

    // act
    mrxService.initialize(mock(PlayerInfo.class), mock(Position.class), mock(Tickets.class));

    // assert
    assertThat(getField(mrxService, "mrxGameStatus")).isNotNull();
  }

  @Test
  public void testSetDetectivesPosition_whenCalled_expectGameStatusIsSet() {
    // arrange
    MrxGameStatus mrxGameStatus = mock(MrxGameStatus.class);
    setField(mrxService, "mrxGameStatus", mrxGameStatus);

    // act
    mrxService.setDetectivesPosition(mock(PlayerInfo.class), mock(Position.class));

    // assert
    verify(mrxGameStatus).setDetectivesPosition(any(PlayerInfo.class), any(Position.class));
  }

  @Test
  public void testGiveTicket_whenCalled_expectGameStatusIsSet() {
    // arrange
    MrxGameStatus mrxGameStatus = mock(MrxGameStatus.class);
    setField(mrxService, "mrxGameStatus", mrxGameStatus);

    // act
    mrxService.giveTicket(mock(Ticket.class));

    // assert
    verify(mrxGameStatus).giveTicket(any(Ticket.class));
  }

  @Test
  public void testNextMove_whenCalled_expectMrxServiceIsCalledAndMrxGameStatusIsSet() {
    // arrange
    MrxGameStatus mrxGameStatus = mock(MrxGameStatus.class);
    setField(mrxService, "mrxGameStatus", mrxGameStatus);
    Move move = mock(Move.class);
    when(mrxMove.nextMove(mrxGameStatus)).thenReturn(move);
    ArgumentCaptor<Move> captor = ArgumentCaptor.forClass(Move.class);

    // act
    Move resultMove = mrxService.nextMove();

    // assert
    verify(mrxMove).nextMove(mrxGameStatus);
    verify(mrxGameStatus).doMove(captor.capture());
    assertThat(move).isSameAs(captor.getValue());
    assertThat(resultMove).isSameAs(move);
  }

}