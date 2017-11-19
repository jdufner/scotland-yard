package de.jdufner.scotland.yard.model.spiel;

import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;
import de.jdufner.scotland.yard.model.spieler.Spieler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Spiel {

  private List<Spieler> spielers = new ArrayList<>();

  public Spiel() {
    spielers.add(new MrX(Startposition.zieheFreiStartposition()));
    spielers.add(new Detektiv(Startposition.zieheFreiStartposition()));
    spielers.add(new Detektiv(Startposition.zieheFreiStartposition()));
    spielers.add(new Detektiv(Startposition.zieheFreiStartposition()));
    spielers.add(new Detektiv(Startposition.zieheFreiStartposition()));
  }

  @Override
  public String toString() {
    return "Spieler: {" + spielers.toString() + "}";
  }

}
