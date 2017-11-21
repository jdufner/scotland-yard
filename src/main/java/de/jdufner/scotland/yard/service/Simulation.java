package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.spiel.Spiel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Component
public class Simulation {

  private final SpielService spielService;

  public Simulation(final SpielService spielService) {
    this.spielService = spielService;
  }

  @PostConstruct
  public void starteSimulation() {
    Spiel spiel = new Spiel();
    //spielService.ermittleKuerzesteDistanzenZwischenJeweilsAllenKnoten(spiel);
    spielService.setzeSpieler(spiel.getSpieler());
    // Spielzug von Mr. X
    spielService.findeNachbarAmWeitestenEntferntVonDetektiven();
    // Spielzüge von Detektiven
    spielService.findeWegZuUndergroundInDreiZuegen();
  }

}
