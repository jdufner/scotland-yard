package de.jdufner.scotland.yard.model.spiel;

import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.service.DetektivService;
import de.jdufner.scotland.yard.service.MrXService;
import de.jdufner.scotland.yard.service.SpielService;
import de.jdufner.scotland.yard.service.SpielbrettService;
import de.jdufner.scotland.yard.service.SpielerService;
import de.jdufner.scotland.yard.service.StartpositionService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class SpielServiceCT {

  private SpielService spielService;
  private SpielbrettService spielbrettService;
  private StartpositionService startpositionService;

  @Before
  public void setUp() {
    final ArrayList<SpielerService> spielerServices = new ArrayList<>();
    startpositionService = mock(StartpositionService.class);
    spielbrettService = mock(SpielbrettService.class);
    spielerServices.add(new MrXService(spielbrettService, startpositionService));
    spielerServices.add(new DetektivService(spielbrettService, startpositionService));
    spielService = new SpielService(spielerServices);
  }

  @Test
  public void testErzeugeSpiel_whenErzeugt_expectFuenfSpielerNichtBeendet() {
    // arrange
    when(startpositionService.zieheFreieStartposition()).thenReturn(new Startposition(1))
        .thenReturn(new Startposition(2))
        .thenReturn(new Startposition(3))
        .thenReturn(new Startposition(4))
        .thenReturn(new Startposition(5));

    // act
    Spiel spiel = spielService.erzeugeSpiel();

    // arrange
    assertThat(spiel.isBeendet()).isFalse();
    assertThat(spiel.getSpieler().size()).isEqualTo(5);
  }

  @Test
  public void testErzeugeSpiel_whenErzeugt_expectFuenfSpielerBeendet() {
    // arrange
    when(startpositionService.zieheFreieStartposition()).thenReturn(new Startposition(1))
        .thenReturn(new Startposition(1))
        .thenReturn(new Startposition(2))
        .thenReturn(new Startposition(3))
        .thenReturn(new Startposition(4));

    // act
    Spiel spiel = spielService.erzeugeSpiel();

    // arrange
    assertThat(spiel.isBeendet()).isTrue();
    assertThat(spiel.getSpieler().size()).isEqualTo(5);
  }

}
