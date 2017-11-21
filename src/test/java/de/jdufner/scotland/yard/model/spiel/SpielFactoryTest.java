package de.jdufner.scotland.yard.model.spiel;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class SpielFactoryTest {

  @Test
  public void toString_whenInitialized_expectFuenfSpieler() {
    // arrange

    // act
    Spiel spiel = SpielFactory.erzeugeSpiel();

    // arrange
    assertThat(spiel.getSpieler().size()).isEqualTo(5);
  }

}
