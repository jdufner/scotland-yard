package de.jdufner.scotland.yard.model.spiel;

import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
