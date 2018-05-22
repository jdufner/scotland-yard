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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.jdufner.scotland.yard.model.spiel.Spiel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimulationTest {

  @InjectMocks
  private Simulation simulation;

  @Mock
  private SpielService spielService;

  @Mock
  private SpielbrettService spielbrettService;

  @Test
  public void testStart() {
    // arrange
    Spiel spiel = mock(Spiel.class);
    when(spielService.erzeugeSpiel()).thenReturn(spiel);
    when(spiel.isBeendet()).thenReturn(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);

    // act
    simulation.starteSimulation();

    // assert
    verify(spielService, times(2)).naechsteRunde(any(Spiel.class));
  }

}
