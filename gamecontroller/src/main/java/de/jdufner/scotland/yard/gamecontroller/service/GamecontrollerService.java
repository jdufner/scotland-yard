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
 */

package de.jdufner.scotland.yard.gamecontroller.service;

import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Der {@link GamecontrollerService} steuert das Zusammenspiel der Figuren mit dem Spielbrett.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class GamecontrollerService {

  private static final Logger LOG = LoggerFactory.getLogger(GamecontrollerService.class);

  private final GameInitializationService gameInitializationService;
  private final GameLapService gameLapService;

  public GamecontrollerService(final GameInitializationService gameInitializationService,
                               final GameLapService gameLapService) {
    this.gameInitializationService = gameInitializationService;
    this.gameLapService = gameLapService;
  }

  @PostConstruct
  public void startGame() {
    final Game game = gameInitializationService.initializeGame();
//    while (!game.isFinished()) {
//      gameLapService.nextLap(game);
//    }
  }

}
