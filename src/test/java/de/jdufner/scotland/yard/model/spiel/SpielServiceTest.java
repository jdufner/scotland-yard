package de.jdufner.scotland.yard.model.spiel;

import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.service.SpielService;
import de.jdufner.scotland.yard.service.StartpositionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class SpielServiceTest {

  @InjectMocks
  private SpielService spielService;

  @Mock
  private StartpositionService startpositionService;

  @Test
  public void testErzeugeSpiel_whenErzeugt_expectFuenfSpielerNichtBeendet() {
    // arrange
    when(startpositionService.zieheFreieStartposition()).thenReturn(new Startposition(13),
        new Startposition(26), new Startposition(29), new Startposition(34), new Startposition(50));

    // act
    Spiel spiel = spielService.erzeugeSpiel();

    // arrange
    assertThat(spiel.isBeendet()).isFalse();
    assertThat(spiel.getSpieler().size()).isEqualTo(5);
  }

  @Test
  public void testErzeugeSpiel_whenErzeugt_expectFuenfSpielerBeendet() {
    // arrange
    when(startpositionService.zieheFreieStartposition()).thenReturn(new Startposition(13),
        new Startposition(13), new Startposition(26), new Startposition(29), new Startposition(34));

    // act
    Spiel spiel = spielService.erzeugeSpiel();

    // arrange
    assertThat(spiel.isBeendet()).isTrue();
    assertThat(spiel.getSpieler().size()).isEqualTo(5);
  }

}
