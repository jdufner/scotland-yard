package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.MrX;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Jürgen DUfner
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class MrXServiceTest {

  @InjectMocks
  private MrXService mrXService;

  @Mock
  private SpielbrettService spielbrettService;
  @Mock
  private StartpositionService startpositionService;

  @Test
  public void test() {
    // arrange

    // act
    PositionUndTickets positionUndTickets =
        mrXService.ermittleNächstenZug(mock(Spiel.class), mock(MrX.class));

    // assert
    assertThat(positionUndTickets).isNotNull();
    verify(spielbrettService).findeNachbarAmWeitestenEntferntVonDetektiven();
  }

}