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

package de.jdufner.scotland.yard.gamecontroller.service;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Der {@link SpielsteuerungService} steuert das Zusammenspiel der Figuren mit dem Spielbrett.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class SpielsteuerungService {

  private static final Logger LOG = LoggerFactory.getLogger(SpielsteuerungService.class);

  private final GameService gameService;
  private final SpielbrettService spielbrettService;

  public SpielsteuerungService(final GameService gameService, final SpielbrettService spielbrettService) {
    this.gameService = gameService;
    this.spielbrettService = spielbrettService;
  }

  @PostConstruct
  public void starteSpiel() {
//    final Game spiel = spielService.initializeGame();
//    while (!spiel.isFinished()) {
//      spielService.nextLap(spiel);
//    }
  }

}
