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

package de.jdufner.scotland.yard.gamecontroller.spiel;

import de.jdufner.scotland.yard.common.position.StartPosition;
import de.jdufner.scotland.yard.gamecontroller.service.BoardService;
import de.jdufner.scotland.yard.gamecontroller.service.GameInitializationService;
import de.jdufner.scotland.yard.gamecontroller.service.StartPositionService;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class GameInitializationServiceCT {

  private GameInitializationService gameInitializationService;
  private BoardService boardService;
  private StartPositionService startPositionService;

  @Before
  public void setUp() {
    startPositionService = mock(StartPositionService.class);
    boardService = mock(BoardService.class);
//    spielService = new SpielService(new MrXService(spielbrettService, startpositionService), new DetektivService(spielbrettService, startpositionService));
  }

  @Test
  public void testErzeugeSpiel_whenErzeugt_expectFuenfSpielerNichtBeendet() {
    // arrange
    when(startPositionService.zieheFreieStartPosition()).thenReturn(new StartPosition(1))
        .thenReturn(new StartPosition(2))
        .thenReturn(new StartPosition(3))
        .thenReturn(new StartPosition(4))
        .thenReturn(new StartPosition(5));

    // act
//    Game spiel = spielService.initializeGame();

    // arrange
//    assertThat(spiel.isFinished()).isFalse();
//    assertThat(spiel.getPlayers().size()).isEqualTo(5);
  }

  @Test
  public void testErzeugeSpiel_whenErzeugt_expectFuenfSpielerBeendet() {
    // arrange
    when(startPositionService.zieheFreieStartPosition()).thenReturn(new StartPosition(1))
        .thenReturn(new StartPosition(1))
        .thenReturn(new StartPosition(2))
        .thenReturn(new StartPosition(3))
        .thenReturn(new StartPosition(4));

    // act
//    Game spiel = spielService.initializeGame();

    // arrange
//    assertThat(spiel.isFinished()).isTrue();
//    assertThat(spiel.getPlayers().size()).isEqualTo(5);
  }

}
