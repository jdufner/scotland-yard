package de.jdufner.scotland.yard.model.spiel;

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class SpielTest {

  @Test
  public void toString_whenSpieleRunden_expectAnzahlSpielrunden() {
    // arrange
    MrX mrX = spy(new MrX(Startposition.zieheFreieStartposition()));
    doAnswer(new Answer() {
      @Override
      public Object answer(final InvocationOnMock invocationOnMock) throws Throwable {
        MrX m = (MrX) invocationOnMock.getMock();
        m.getPositions().add(new Position(1));
        return null;
      }
    }).when(mrX).ziehe();
    List<Detektiv> detektivs = new ArrayList<>();
    Detektiv detektiv = spy(new Detektiv(Startposition.zieheFreieStartposition()));
    detektivs.add(detektiv);
    doAnswer(new Answer() {
      @Override
      public Object answer(final InvocationOnMock invocationOnMock) throws Throwable {
        Detektiv d = (Detektiv) invocationOnMock.getMock();
        d.getPositions().add(new Position(1));
        return null;
      }
    }).when(detektiv).ziehe();
    Spiel spiel = new Spiel(mrX, detektivs);

    // act
    spiel.spieleRunden();

    // arrange
  }

}
