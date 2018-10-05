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

package de.jdufner.scotland.yard.gameboard.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import de.jdufner.scotland.yard.gameboard.config.Neo4jConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Neo4jConfig.class)
@TestPropertySource(properties = {"neo4j.database.directory=./neo4j-data", "neo4j.dbms.directories.import=../../src/main/resources"})
//@TestPropertySource(properties = {"neo4j.database.directory=../neo4j-data"})
public class BoardServiceCT {

  @Autowired
  private BoardService boardService;

  @Test
  public void testIsMoveValid_whenValidMove_expectTrue() {
    // arrange
    Move move = new Move(Mockito.mock(PlayerInfo.class), new Position(1), new Position(8), new TaxiTicket(1));

    // act
    boolean valid = boardService.isMoveValid(move);

    // assert
    assertThat(valid).isTrue();
  }

  @Test
  public void testIsMoveValid_whenValidIsNotMove_expectFalse() {
    // arrange
    Move move = new Move(Mockito.mock(PlayerInfo.class), new Position(1), new Position(2), new TaxiTicket(1));

    // act
    boolean valid = boardService.isMoveValid(move);

    // assert
    assertThat(valid).isFalse();
  }

}





