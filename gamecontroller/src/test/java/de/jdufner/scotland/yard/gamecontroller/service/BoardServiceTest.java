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

import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class BoardServiceTest {

  @InjectMocks
  public BoardService boardService;

  @Mock
  public GraphDatabaseService graphDatabaseService;

  @Test
  public void testIsMoveValid_whenPathExists_expectTrue() {
    // arrange
    Move move = new Move(
        PlayerInfo.Builder.newMrx().build(),
        new Position(13),
        new Position(14),
        new TaxiTicket(1)
    );
    when(graphDatabaseService.beginTx()).thenReturn(mock(Transaction.class));
    Result result = mock(Result.class);
    when(graphDatabaseService.execute(anyString())).thenReturn(result);
    when(result.hasNext()).thenReturn(true);

    // act
    boolean valid = boardService.isMoveValid(move);

    // assert
    assertThat(valid).isTrue();
    verify(graphDatabaseService).execute(anyString());
  }

  @Test
  public void testIsMoveValid_whenPathNotExists_expectFalse() {
    // arrange
    Move move = new Move(
        PlayerInfo.Builder.newMrx().build(),
        new Position(13),
        new Position(14),
        new TaxiTicket(1)
    );
    when(graphDatabaseService.beginTx()).thenReturn(mock(Transaction.class));
    when(graphDatabaseService.execute(anyString())).thenReturn(mock(Result.class));

    // act
    boolean valid = boardService.isMoveValid(move);

    // assert
    assertThat(valid).isFalse();
    verify(graphDatabaseService).execute(anyString());
  }

}