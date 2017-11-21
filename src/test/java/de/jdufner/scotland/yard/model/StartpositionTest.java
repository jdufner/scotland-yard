package de.jdufner.scotland.yard.model;

import de.jdufner.scotland.yard.model.position.Startposition;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class StartpositionTest {

  private static Logger LOG = LoggerFactory.getLogger(StartpositionTest.class);

  @Test
  public void testFactoryMethode() {
    // arrange

    // act
    Startposition startposition = Startposition.zieheFreieStartposition();

    // assert
    //LOG.debug(String.valueOf(startposition.getPosition()));
    System.out.println((startposition.getPosition()));
    assertThat(startposition.getPosition()).isBetween(1, 199);
  }

}