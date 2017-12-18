package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class SpielerServiceTest {

  @InjectMocks
  @Spy
  private TestSpielerService spielerService;

  @Mock
  private SpielbrettService spielbrettService;
  @Mock
  private StartpositionService startpositionService;

  @Test
  public void test() {
    // arrange

    // act
    spielerService.fuehreZugDurch(mock(Spiel.class), mock(Spieler.class));

    // assert
    verify(spielerService).ermittleNächstenZug(any(Spiel.class), any(Spieler.class));
    verify(spielbrettService).verschiebeSpieler(any(Spieler.class));
  }

  private static class TestSpielerService extends SpielerService {

    public TestSpielerService(final SpielbrettService spielbrettService,
                              final StartpositionService startpositionService) {
      super(spielbrettService, startpositionService);
    }

    @Override
    public Spieler erzeugeSpieler() {
      return null;
    }

    @Override
    protected PositionUndTickets ermittleNächstenZug(final Spiel spiel, final Spieler spieler) {
      return null;
    }
  }

}
