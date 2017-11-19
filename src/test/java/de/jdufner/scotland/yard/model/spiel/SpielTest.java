package de.jdufner.scotland.yard.model.spiel;

import org.junit.Test;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class SpielTest {

  @Test
  public void toString_whenInitialized_expectSpieler() {
    // arrange
    Spiel spiel = new Spiel();

    // act
    System.out.println(spiel);
  }
}