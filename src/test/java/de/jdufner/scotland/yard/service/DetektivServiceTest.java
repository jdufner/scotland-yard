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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.zug.Zug;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Jürgen DUfner
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class DetektivServiceTest {

  @InjectMocks
  private DetektivService detektivService;

  @Mock
  private SpielbrettService spielbrettService;
  @Mock
  private StartpositionService startpositionService;

  @Test
  public void test() {
    // arrange
    Spiel spiel = mock(Spiel.class);
    when(spiel.getAktuelleRunde()).thenReturn(1);

    // act
    Zug zug =
        detektivService.ermittleNächstenZug(spiel, mock(Detektiv.class));

    // assert
    assertThat(zug).isNotNull();
    verify(spielbrettService).findeWegZuUndergroundInAnzahlZuegen(any(Detektiv.class), anyInt());
  }

}