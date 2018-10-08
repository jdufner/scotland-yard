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

package de.jdufner.scotland.yard.gameboard.service;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.move.Path;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.BusTicket;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import de.jdufner.scotland.yard.common.ticket.UndergroundTicket;
import de.jdufner.scotland.yard.gameboard.config.Neo4jConfig;
import java.util.List;
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

  @Test
  public void testFindAllPossiblePathes_whenStart13_expect10Pathes() {
    // arrange
    Position position = new Position(13);

    // act
    List<Path> allPossiblePathes = boardService.findAllPathes(position);

    // assert
    assertThat(allPossiblePathes)
        .containsExactlyInAnyOrder(
            new Path(new Position(13), new Position(4), new TaxiTicket(1)),
            new Path(new Position(13), new Position(14), new BusTicket(1)),
            new Path(new Position(13), new Position(14), new TaxiTicket(1)),
            new Path(new Position(13), new Position(23), new BusTicket(1)),
            new Path(new Position(13), new Position(23), new TaxiTicket(1)),
            new Path(new Position(13), new Position(24), new TaxiTicket(1)),
            new Path(new Position(13), new Position(46), new UndergroundTicket(1)),
            new Path(new Position(13), new Position(52), new BusTicket(1)),
            new Path(new Position(13), new Position(67), new UndergroundTicket(1)),
            new Path(new Position(13), new Position(89), new UndergroundTicket(1))
        )
    ;
  }

  @Test
  public void testDeterminePossiblePositionsByTickets_whenNoTicket_expectStartPosition() {
    // arrange
    Position position = new Position(13);

    // act
    List<Position> positions = boardService.determinePossiblePositionsByTickets(position, null);

    // assert
    assertThat(positions).containsExactly(position);
  }

  @Test
  public void testDeterminePossiblePositionsByTickets_whenOneTaxiTicket_expectPositions() {
    // arrange
    Position position = new Position(13);

    // act
    List<Position> positions = boardService.determinePossiblePositionsByTickets(position, new TaxiTicket(1));

    // assert
    assertThat(positions).containsExactlyInAnyOrder(
        new Position(4), new Position(14), new Position(23), new Position(24));
  }

  @Test
  public void testDeterminePossiblePositionsByTickets_whenTwoTaxiTickets_expectPositions() {
    // arrange
    Position position = new Position(13);

    // act
    List<Position> positions = boardService.determinePossiblePositionsByTickets(position, new TaxiTicket(1), new TaxiTicket(1));

    // assert
    assertThat(positions).containsExactlyInAnyOrder(
        new Position(3), new Position(4), new Position(12), new Position(14), new Position(15),
        new Position(22), new Position(23), new Position(24), new Position(25), new Position(37),
        new Position(38));
  }

  @Test
  public void testDeterminePossiblePositionsByTickets_whenUndergroundAndTaxiTickets_expectPositions() {
    // arrange
    Position position = new Position(13);

    // act
    List<Position> positions = boardService.determinePossiblePositionsByTickets(position, new UndergroundTicket(1), new TaxiTicket(1));

    // assert
    assertThat(positions).containsExactlyInAnyOrder(
        new Position(33), new Position(45), new Position(46), new Position(47), new Position(51),
        new Position(61), new Position(66), new Position(67), new Position(68), new Position(71),
        new Position(84), new Position(88), new Position(89), new Position(105));
  }

  @Test
  public void testFindPositionsNextToMrxFarAwayFromDetectives_whenCalled_expectResult() {
    // arrange

    // act
    boardService.findPositionsNextToMrxFarAwayFromDetectives(new Position(13), asList(new Position(1), new Position(82)));

    // assert
  }

}
