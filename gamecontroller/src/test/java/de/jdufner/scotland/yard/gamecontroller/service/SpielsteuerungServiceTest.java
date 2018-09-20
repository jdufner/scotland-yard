/*
 * Scotland Yard is a spielsteuerungService of the board game.
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

import static org.mockito.Mockito.mock;

import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SpielsteuerungServiceTest {

  @InjectMocks
  private SpielsteuerungService spielsteuerungService;

  @Mock
  private GameService gameService;

  @Mock
  private SpielbrettService spielbrettService;

  @Test
  public void whenStarteSpiel_expectSpielEndetNachDreiRunden() {
    // arrange
    Game game = mock(Game.class);
//    when(spielService.initializeGame()).thenReturn(game);
//    when(game.isFinished()).thenReturn(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);

    // act
    spielsteuerungService.starteSpiel();

    // assert
//    verify(spielService, times(2)).nextLap(any(Game.class));
  }

}
