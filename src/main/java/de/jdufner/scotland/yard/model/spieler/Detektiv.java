package de.jdufner.scotland.yard.model.spieler;

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

  public Detektiv(final Startposition startposition) {
    super(startposition);
    tickets.add(new Taxi(10));
    tickets.add(new Bus(8));
    tickets.add(new Underground(4));
  }

  @Override
  public String toString() {
    return "Detektiv: " + super.toString();
  }

  public static class Builder {

    private List<Detektiv> detektivs = new ArrayList<>();

    public Builder(final int... startpositionAsInts) {
      for (final int startpositionAsInt : startpositionAsInts) {
        detektivs.add(new Detektiv(new Startposition(startpositionAsInt)));
      }
    }

    public List<Detektiv> build() {
      return detektivs;
    }

  }

}
