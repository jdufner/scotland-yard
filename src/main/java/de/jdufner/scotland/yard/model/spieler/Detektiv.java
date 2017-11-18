package de.jdufner.scotland.yard.model.spieler;

import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.tickets.Bus;
import de.jdufner.scotland.yard.model.tickets.Taxi;
import de.jdufner.scotland.yard.model.tickets.Underground;

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

}
