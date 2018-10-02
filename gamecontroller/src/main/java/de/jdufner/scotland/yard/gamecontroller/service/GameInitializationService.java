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

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.MrxService;
import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Detective;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Mrx;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The {@link GameInitializationService} creates a {@link Game} containing the {@link de.jdufner.scotland.yard.gamecontroller.model.spieler.Player},
 * in detail one {@link Mrx} and up to four {@link Detective}s.
 *
 * Each player has a {@link Position} and a certain number of {@link de.jdufner.scotland.yard.common.ticket.Ticket}s.
 *
 * @author Jürgen Dufner
 * @since 1.0
 * @see PlayerInfoService
 * @see StartPositionService
 * @see StartTicketService
 */
@Service
public class GameInitializationService {

  private static final Logger LOG = LoggerFactory.getLogger(GameInitializationService.class);

  private final StartPositionService startPositionService;
  private final StartTicketService startTicketService;
  private final MrxService mrxService;
  private final DetectiveService detectiveService;
  private final PlayerInfoService playerInfoService;

  public GameInitializationService(final PlayerInfoService playerInfoService,
                                   final StartPositionService startPositionService,
                                   final StartTicketService startTicketService,
                                   final MrxService mrxService,
                                   final DetectiveService detectiveService) {
    this.playerInfoService = playerInfoService;
    this.startPositionService = startPositionService;
    this.startTicketService = startTicketService;
    this.mrxService = mrxService;
    this.detectiveService = detectiveService;
  }

  public Game initializeGame() {
    LOG.debug("initialize game");
    Mrx mrx = initializeMrx();
    List<Detective> detectives = initializeDetectives();
    Game game = new Game(mrx, detectives);
    LOG.debug("game {} initialized", game);
    return game;
  }

  private List<Detective> initializeDetectives() {
    List<Detective> detectives = IntStream.rangeClosed(1, 4)
        .mapToObj(number -> initializeDetective(number))
        .collect(Collectors.toList());
    return Collections.unmodifiableList(detectives);
  }

  private Detective initializeDetective(final int number) {
    final Position startPosition = startPositionService.zieheFreieStartPosition();
    final Tickets detectiveTickets = startTicketService.getDetectiveTickets();
    final PlayerInfo playerInfo = playerInfoService.getDetectivePlayerInfo();
    final Detective detective = new Detective(number, playerInfo, startPosition, detectiveTickets);
    detectiveService.initialize(playerInfo, startPosition, detectiveTickets);
    // TODO [jdufner, 2018-09-20] When and how will be set the players on the board?
    return detective;
  }

  private Mrx initializeMrx() {
    final PlayerInfo playerInfo = playerInfoService.getMrxPlayerInfo();
    final Position startPosition = startPositionService.zieheFreieStartPosition();
    final Tickets mrxTickets = startTicketService.getMrxTickets();
    final Mrx mrx = new Mrx(playerInfo, startPosition, mrxTickets);
    mrxService.initialize(playerInfo, startPosition, mrxTickets);
    // TODO [jdufner, 2018-09-20] When and how will be set the players on the board?
    return mrx;
  }

}
