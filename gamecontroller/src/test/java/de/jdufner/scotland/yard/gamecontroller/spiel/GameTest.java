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

package de.jdufner.scotland.yard.gamecontroller.spiel;

import static org.assertj.core.api.Assertions.assertThat;

import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Detective;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Mrx;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class GameTest {

  private Game game;

  @Test
  public void testConstructor() {
    // arrange
    final Mrx mrX = Mrx.Builder.defaultMrx().withStartpositionAsInt(13).build();
    final List<Detective> detectives = new ArrayList<>();
    detectives.add(Detective.Builder.defaultDetective().withStartpositionAsInt(26).build());
    detectives.add(Detective.Builder.defaultDetective().withStartpositionAsInt(29).build());
    detectives.add(Detective.Builder.defaultDetective().withStartpositionAsInt(34).build());
    detectives.add(Detective.Builder.defaultDetective().withStartpositionAsInt(50).build());

    // act
    game = new Game(mrX, detectives);

    // assert
    assertThat(game.getPlayers()).containsExactly(mrX, detectives.get(0), detectives
        .get(1), detectives.get(2), detectives.get(3));
    assertThat(game.isFinished()).isFalse();
    assertThat(game.getCurrentLap()).isEqualTo(0);
  }

  @Test
  public void
  testIsBeendet_whenEinundzwanzigRundenGespielt_expectSpielBeendet() {
    // arrange
    final Mrx mrX = Mrx.Builder.defaultMrx().withStartpositionAsInt(13).build();
    final List<Detective> detectives = new ArrayList<>();
    detectives.add(Detective.Builder.defaultDetective().withStartpositionAsInt(26).build());
    detectives.add(Detective.Builder.defaultDetective().withStartpositionAsInt(29).build());
    detectives.add(Detective.Builder.defaultDetective().withStartpositionAsInt(34).build());
    detectives.add(Detective.Builder.defaultDetective().withStartpositionAsInt(50).build());
    game = new Game(mrX, detectives);

    // act
    while (!game.isFinished()) {
      game.nextLap();
    }

    // assert
    assertThat(game.isFinished()).isTrue();
    assertThat(game.getCurrentLap()).isEqualTo(21);
  }

}
