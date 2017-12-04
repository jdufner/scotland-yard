package de.jdufner.scotland.yard.model.spieler;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.tickets.BlackTicket;
import de.jdufner.scotland.yard.model.tickets.Bus;
import de.jdufner.scotland.yard.model.tickets.Doppelzug;
import de.jdufner.scotland.yard.model.tickets.Taxi;
import de.jdufner.scotland.yard.model.tickets.Underground;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class MrX extends Spieler {

  private static final int[] auftauchen = {3, 8, 13, 18};

  public MrX(final Startposition startposition) {
    super(startposition);
    tickets.add(new Taxi(4));
    tickets.add(new Bus(3));
    tickets.add(new Underground(3));
    tickets.add(new Doppelzug(2));
    tickets.add(new BlackTicket(2));
  }

  @Override
  public String toString() {
    return "Mr. X: " + super.toString();
  }

  @Override
  public void zieheUndVerbraucheTickets(final PositionUndTickets positionUndTickets) {
    zieheAuf(positionUndTickets.getPosition());
    verbraucheTickets(positionUndTickets.getTickets());
  }

  public static class Builder {


    private MrX mrX;

    public Builder(final int startpositionAsInt) {
      mrX = new MrX(new Startposition(startpositionAsInt));
    }

    public MrX build() {
      return mrX;
    }

  }

}
