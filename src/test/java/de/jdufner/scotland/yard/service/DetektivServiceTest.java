package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    PositionUndTickets positionUndTickets =
        detektivService.ermittleNächstenZug(spiel, mock(Detektiv.class));

    // assert
    assertThat(positionUndTickets).isNotNull();
    verify(spielbrettService).findeWegZuUndergroundInAnzahlZuegen(any(Detektiv.class), anyInt());
  }

}