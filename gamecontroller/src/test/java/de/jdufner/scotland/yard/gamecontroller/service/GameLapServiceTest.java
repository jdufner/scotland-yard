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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.MrxService;
import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.BlackTicket;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import de.jdufner.scotland.yard.common.ticket.UndergroundTicket;
import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Detective;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Mrx;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GameLapServiceTest {

  @InjectMocks
  private GameLapService gameLapService;

  @Mock
  private MrxService mrxService;
  @Mock
  private DetectiveService detectiveService;
  @Mock
  private SpielbrettService spielbrettService;

  @Test
  public void testNextLap_whenGameAndMovesAreValid_expectPlayerServicesHaveBeenCalled() {
    // arrange
    Game game = Game.Builder.newGameWithFourDetectives().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(1),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(0).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(0).getPlayerInfo(),
            game.getDetectives().get(0).getCurrentPosition(),
            new Position(27),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(1).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(1).getPlayerInfo(),
            game.getDetectives().get(1).getCurrentPosition(),
            new Position(30),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(2).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(2).getPlayerInfo(),
            game.getDetectives().get(2).getCurrentPosition(),
            new Position(35),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(3).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(3).getPlayerInfo(),
            game.getDetectives().get(3).getCurrentPosition(),
            new Position(51),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);

    // assert
    verify(mrxService).nextMove();
    verify(detectiveService, times(4)).nextMove(any(PlayerInfo.class));
    verify(spielbrettService, times(5)).isMoveValid(any(Move.class));
    assertThat(game.isFinished()).isFalse();
  }

  @Test
  public void testNextLap_whenDetectiveCatchesMrx_expectDetectiveHasCaughtMrx() {
    // arrange
    Game game = Game.Builder.newGameWithOneDetective().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(1),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(0).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(0).getPlayerInfo(),
            game.getDetectives().get(0).getCurrentPosition(),
            new Position(1),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);

    // assert
    verify(mrxService).nextMove();
    verify(detectiveService).nextMove(any(PlayerInfo.class));
    assertThat(game.isFinished()).isTrue();
  }

  @Test
  public void testNextLap_whenMrxMustBeShownToDetective_expectDetectiveServiceHasBeenCalled() {
    // arrange
    Game game = Game.Builder.newGameWithOneDetective()
        .withCurrentLap(2)
        .build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(14),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(0).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(0).getPlayerInfo(),
            game.getDetectives().get(0).getCurrentPosition(),
            new Position(27),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);

    // assert
    verify(mrxService).nextMove();
    verify(detectiveService).nextMove(any(PlayerInfo.class));
    verify(detectiveService).showMrx(game.getDetectives().get(0).getPlayerInfo(), game.getMrx().getCurrentPosition());
  }

  @Test(expected = WrongStartPositionException.class)
  public void testNextLap_whenMrxStartsFromWrongPosition_expectException() {
    // arrange
    Game game = Game.Builder.newGameWithOneDetective().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            new Position(1),
            game.getMrx().getCurrentPosition(),
            new TaxiTicket(1)));

    // act
    gameLapService.nextLap(game);
  }

  @Test(expected = WrongStartPositionException.class)
  public void testNextLap_whenDetectiveStartsFromWrongPosition_expectException() {
    // arrange
    Game game = Game.Builder.newGameWithOneDetective().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(1),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(0).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(0).getPlayerInfo(),
            new Position(1),
            game.getDetectives().get(0).getCurrentPosition(),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);
  }

  @Test(expected = PositionInUseException.class)
  public void testNextLap_whenMrxMovesToDetective_expectException() {
    // arrange
    Game game = Game.Builder.newGameWithOneDetective().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            game.getDetectives().get(0).getCurrentPosition(),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);
  }

  @Test
  public void testNextLap_whenMoveIsValid_expectAllPlayersHaveDoneAnAllowedMoveOnTheBoard() {
    // arrange
    Game game = Game.Builder.newGameWithOneDetective().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(14),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(0).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(0).getPlayerInfo(),
            game.getDetectives().get(0).getCurrentPosition(),
            new Position(27),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);

    // assert
    verify(spielbrettService, times(2)).isMoveValid(any(Move.class));
    assertThat(game.getMrx().getCurrentPosition()).isEqualTo(new Position(14));
    assertThat(game.getDetectives().get(0).getCurrentPosition()).isEqualTo(new Position(27));
  }

  @Test(expected = InvalidMoveException.class)
  public void testNextLap_whenMrxMoveIsInvalid_expectException() {
    // arrange
    Game game = Game.Builder.newGameWithOneDetective().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(14),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(false);

    // act
    gameLapService.nextLap(game);
  }

  @Test(expected = InvalidMoveException.class)
  public void testNextLap_whenDetectivesMoveIsInvalid_expectException() {
    // arrange
    Game game = Game.Builder.newGameWithOneDetective().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(14),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(0).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(0).getPlayerInfo(),
            game.getDetectives().get(0).getCurrentPosition(),
            new Position(27),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true, false);

    // act
    gameLapService.nextLap(game);
  }

  @Test(expected = PositionInUseException.class)
  public void testNextLap_whenDetectiveMovesToAnotherDetective_expectException() {
    // arrange
    Game game = Game.Builder.newGameWithFourDetectives().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(1),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(0).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(0).getPlayerInfo(),
            game.getDetectives().get(0).getCurrentPosition(),
            game.getDetectives().get(1).getCurrentPosition(),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);
  }

  @Test(expected = OutOfTicketsException.class)
  public void testNextLap_whenMrxHasNotEnoughTickets_expectException() {
    // arrange
    Game game = new Game.Builder()
        .withCurrentLap(0)
        .withMrx(new Mrx.Builder()
            .withPlayerInfo(PlayerInfo.Builder.newMrx().build())
            .withStartpositionAsInt(13)
            .withTickets(new Tickets.Builder()
                .withTicket(new BlackTicket(1))
                .build())
            .build())
        .withDetective(Detective.Builder.defaultDetective().withNummer(1).withStartpositionAsInt(26).build())
        .build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(14),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);
  }

  @Test(expected = OutOfTicketsException.class)
  public void testNextLap_whenDetectiveHasNotEnoughTickets_expectException() {
    // arrange
    Game game = new Game.Builder()
        .withCurrentLap(0)
        .withMrx(Mrx.Builder.defaultMrx().build())
        .withDetective(new Detective.Builder()
            .withNummer(1)
            .withPlayerInfo(PlayerInfo.Builder.newDetective().build())
            .withStartpositionAsInt(26)
            .withTickets(new Tickets.Builder()
                .withTicket(new UndergroundTicket(1))
                .build())
            .build())
        .build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(14),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(any(PlayerInfo.class))).thenReturn(
        new Move(
            game.getDetectives().get(0).getPlayerInfo(),
            game.getDetectives().get(0).getCurrentPosition(),
            new Position(27),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);
  }

  @Test
  public void testNextLap_whenDetectiveUsedTicket_expectMrxGotDetectivesTicket() {
    Game game = Game.Builder.newGameWithOneDetective().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            new Position(1),
            new TaxiTicket(1)));
    when(detectiveService.nextMove(game.getDetectives().get(0).getPlayerInfo())).thenReturn(
        new Move(
            game.getDetectives().get(0).getPlayerInfo(),
            game.getDetectives().get(0).getCurrentPosition(),
            new Position(27),
            new TaxiTicket(1)));
    when(spielbrettService.isMoveValid(any(Move.class))).thenReturn(true);

    // act
    gameLapService.nextLap(game);

    // assert
    assertThat(game.getMrx().getTickets().getTickets()).contains(new TaxiTicket(2));
    assertThat(game.getDetectives().get(0).getTickets().getTickets()).contains(new TaxiTicket(3));
  }
}
