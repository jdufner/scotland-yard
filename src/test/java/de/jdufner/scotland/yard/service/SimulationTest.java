package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.spiel.Spiel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
