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

import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Player;
import de.jdufner.scotland.yard.gamecontroller.model.zug.Zug;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceTest {

  @InjectMocks
  @Spy
  private TestPlayerService spielerService;

  @Mock
  private SpielbrettService spielbrettService;
  @Mock
  private StartPositionService startPositionService;

  @Test
  public void test() {
    // arrange

    // act
    spielerService.fuehreZugDurch(mock(Game.class), mock(Player.class));

    // assert
    verify(spielerService).ermittleNächstenZug(any(Game.class), any(Player.class));
    verify(spielbrettService).verschiebeSpieler(any(Player.class));
  }

  private static class TestPlayerService extends SpielerService {

    public TestPlayerService(final SpielbrettService spielbrettService,
                             final StartPositionService startPositionService) {
      super(spielbrettService, startPositionService);
    }

    @Override
    protected Zug ermittleNächstenZug(final Game game, final Player player) {
      return null;
    }
  }

}
