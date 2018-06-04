package de.jdufner.scotland.yard.model.zug;

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.tickets.Ticket;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class DoppelZug extends Zug {

  private Zug ersterZug;
  private Zug zweiterZug;

  public DoppelZug(final Zug ersterZug, final Zug zweiterZug) {
    this.ersterZug = ersterZug;
    this.zweiterZug = zweiterZug;
  }

  public Position getZiel() {
    return zweiterZug.getZiel();
  }

  @Override
  public Ticket getTicket() {
    throw new RuntimeException();
  }

}
