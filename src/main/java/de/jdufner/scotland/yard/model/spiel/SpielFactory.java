package de.jdufner.scotland.yard.model.spiel;

import de.jdufner.scotland.yard.model.position.Startposition;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Dufner
 * @since 0.3
 */
public class SpielFactory {

  public static Spiel erzeugeSpiel() {
    MrX mrX = new MrX(Startposition.zieheFreieStartposition());
    List<Detektiv> detektivs = new ArrayList<>();
    detektivs.add(new Detektiv(Startposition.zieheFreieStartposition()));
    detektivs.add(new Detektiv(Startposition.zieheFreieStartposition()));
    detektivs.add(new Detektiv(Startposition.zieheFreieStartposition()));
    detektivs.add(new Detektiv(Startposition.zieheFreieStartposition()));
    return new Spiel(mrX, detektivs);
  }

}
