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
  private final SpielbrettService spielbrettService;

  public Simulation(final SpielService spielService, final SpielbrettService spielbrettService) {
    this.spielService = spielService;
    this.spielbrettService = spielbrettService;
  }

  @PostConstruct
  public void starteSimulation() {
    final Spiel spiel = spielService.erzeugeSpiel();
    spielbrettService.aktualisiereSpielbrett(spiel);
    while (!spiel.isBeendet()) {
      spielService.naechsteRunde(spiel);
    }
  }

  //spielbrettService.ermittleKuerzesteDistanzenZwischenJeweilsAllenKnoten(spiel);

}
