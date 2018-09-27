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
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
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
  public void whenNextLap_expectPlayerServicesHaveBeenCalled() {
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
  public void whenNextLap_expectDetectiveHasCaughtMrx() {
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

  @Test(expected = RuntimeException.class)
  public void whenNextLap_expectMrxStartsFromWrongPosition() {
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

  @Test(expected = RuntimeException.class)
  public void whenNextLap_expectDetectiveStartsFromWrongPosition() {
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

  @Test(expected = RuntimeException.class)
  public void whenNextLap_expectMrxMustNotMoveToADetective() {
    // arrange
    Game game = Game.Builder.newGameWithOneDetective().build();
    when(mrxService.nextMove()).thenReturn(
        new Move(
            game.getMrx().getPlayerInfo(),
            game.getMrx().getCurrentPosition(),
            game.getDetectives().get(0).getCurrentPosition(),
            new TaxiTicket(1)));

    // act
    gameLapService.nextLap(game);
  }

  @Test
  public void whenNextLapCheckIfMoveIsValid_expectAllPlayersHaveDoneAnAllowedMoveOnTheBoard() {
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

  @Test(expected = RuntimeException.class)
  public void whenNextLapCheckIfMoveIsValid_expectMrxHasHaveDoneAnNotAllowedMoveOnTheBoard() {
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

  @Test(expected = RuntimeException.class)
  public void whenNextLapCheckIfMoveIsValid_expectDetectiveHasDoneAnNotAllowedMoveOnTheBoard() {
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

  @Test(expected = RuntimeException.class)
  public void whenNextLapCheckIfDetectiveMovesToAnotherDetective_expectDetectiveMovesToAnotherDetectivesPosition() {
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

  @Test
  public void whenNextLapCheckIfPlayerHasEnoughTickets_expectMrxHasNotEnoughTickets() {
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

}