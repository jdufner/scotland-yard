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

package de.jdufner.scotland.yard.model.spiel;

import static org.assertj.core.api.Assertions.assertThat;

import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;
import java.util.List;
import org.junit.Test;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class SpielTest {

  private Spiel spiel;

  @Test
  public void testConstructor() {
    // arrange
    final MrX mrX = new MrX.Builder(13).build();
    final List<Detektiv> detektivs = new Detektiv.Builder(26, 29, 34, 50).build();

    // act
    spiel = new Spiel(mrX, detektivs);

    // assert
    assertThat(spiel.getSpieler()).containsExactly(mrX, detektivs.get(0), detektivs
        .get(1), detektivs.get(2), detektivs.get(3));
    assertThat(spiel.isBeendet()).isFalse();
  }

}
