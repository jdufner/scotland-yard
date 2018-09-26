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

import static org.mockito.Mockito.when;

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.MrxService;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import org.assertj.core.api.Assertions;
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
  public void whenNextLap_expect() {
    // arrange
    Game game = Game.Builder.newGame().build();
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

    // act
    gameLapService.nextLap(game);

    // assert
    Assertions.assertThat(game.isFinished()).isFalse();
  }

}