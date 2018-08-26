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

package de.jdufner.scotland.yard.service;

import static org.assertj.core.api.Assertions.assertThat;

import de.jdufner.scotland.yard.model.position.StartPosition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StartPositionServiceTest {

  @InjectMocks
  private StartPositionService underTest;

  @Test
  public void testZieheFreieStartposition_whenZweiGezogen_expectZweiUnterschiedliche() {
    // arrange

    // act
    StartPosition startPosition1 = underTest.zieheFreieStartPosition();
    StartPosition startPosition2 = underTest.zieheFreieStartPosition();

    // assert
    assertThat(startPosition1).isNotEqualTo(startPosition2);
  }

}
