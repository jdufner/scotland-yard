package de.jdufner.scotland.yard.model.spieler;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.tickets.Bus;
import de.jdufner.scotland.yard.model.tickets.Taxi;
import de.jdufner.scotland.yard.model.tickets.Underground;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Detektiv extends Spieler {

  private final Nummer nummer;

  public Detektiv(final Startposition startposition, final int nummer) {
    super(startposition);
    tickets.add(new Taxi(10));
    tickets.add(new Bus(8));
    tickets.add(new Underground(4));
    this.nummer = new Nummer(nummer);
  }

  @Override
  public String toString() {
    return "Detektiv: " + super.toString() + ", " + nummer;
  }

  @Override
  public void zieheUndVerbraucheTickets(final PositionUndTickets positionUndTickets) {

  }

  public static class Builder {


    private List<Detektiv> detektivs = new ArrayList<>();

    public Builder(final int... startpositionAsInts) {
      int nummer = 0;
      for (final int startpositionAsInt : startpositionAsInts) {
        detektivs.add(new Detektiv(new Startposition(startpositionAsInt), nummer++));
      }
    }

    public List<Detektiv> build() {
      return detektivs;
    }

  }

  private class Nummer {
    private final int value;

    private Nummer(final int value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return "Nummer: " + value;
    }
  }

}
