/*
 * Scotland Yard is a simulation of the board game.
 * Copyright (C) 2008-2018  Juergen Dufner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.spiel.Spiel;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Component
public class Simulation {

  private static final Logger LOG = LoggerFactory.getLogger(Simulation.class);

  private final SpielService spielService;
  private final SpielbrettService spielbrettService;

  public Simulation(final SpielService spielService, final SpielbrettService spielbrettService) {
    this.spielService = spielService;
    this.spielbrettService = spielbrettService;
  }

  @PostConstruct
  public void starteSimulation() {
    LOG.info(new Object() {
    }.getClass().getEnclosingMethod().getName());
    final Spiel spiel = spielService.erzeugeSpiel();
    spielbrettService.aktualisiereSpielbrett(spiel);
    while (!spiel.isBeendet()) {
      spielService.naechsteRunde(spiel);
    }
  }

  //spielbrettService.ermittleKuerzesteDistanzenZwischenJeweilsAllenKnoten(spiel);

}
