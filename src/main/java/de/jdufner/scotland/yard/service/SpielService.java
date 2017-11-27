package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Dufner
 * @since 0.3
 */
@Service
public class SpielService {

  private final StartpositionService startpositionService;

  public SpielService(final StartpositionService startpositionService) {
    this.startpositionService = startpositionService;
  }

  public Spiel erzeugeSpiel() {
    MrX mrX = new MrX(startpositionService.zieheFreieStartposition());
    List<Detektiv> detektivs = new ArrayList<>();
    detektivs.add(new Detektiv(startpositionService.zieheFreieStartposition()));
    detektivs.add(new Detektiv(startpositionService.zieheFreieStartposition()));
    detektivs.add(new Detektiv(startpositionService.zieheFreieStartposition()));
    detektivs.add(new Detektiv(startpositionService.zieheFreieStartposition()));
    return new Spiel(mrX, detektivs);
  }

  public void naechsteRunde(final Spiel spiel) {

  }

}
