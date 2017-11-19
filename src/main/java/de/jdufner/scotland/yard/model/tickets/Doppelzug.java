package de.jdufner.scotland.yard.model.tickets;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Doppelzug extends Ticket {

  public Doppelzug(final int anzahl) {
    super(anzahl);
  }

  @Override
  public String toString() {
    return "Doppelzug: " + getAnzahl();
  }

}
