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

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.MrxService;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.StartPosition;
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
 * The {@link GameService} creates a {@link Game} containing the {@link de.jdufner.scotland.yard.gamecontroller.model.spieler.Player},
 * in detail one {@link Mrx} and up to four {@link Detective}s.
 *
 * Each player has a {@link StartPosition} and a certain number of {@link de.jdufner.scotland.yard.common.ticket.Ticket}s.
 *
 * @author Jürgen Dufner
 * @since 1.0
 * @see StartPositionService
 * @see StartTicketService
 */
@Service
public class GameService {

  private static final Logger LOG = LoggerFactory.getLogger(GameService.class);

  private final StartPositionService startPositionService;
  private final StartTicketService startTicketService;
  private final MrxService mrxService;
  private final DetectiveService detectiveService;

  public GameService(final StartPositionService startPositionService,
                     final StartTicketService startTicketService,
                     final MrxService mrxService,
                     final DetectiveService detectiveService) {
    this.startPositionService = startPositionService;
    this.startTicketService = startTicketService;
    this.mrxService = mrxService;
    this.detectiveService = detectiveService;
  }

  public Game initializeGame() {
    Mrx mrx = initializeMrx();
    List<Detective> detectives = initializeDetectives();
    return new Game(mrx, detectives);
  }

  private List<Detective> initializeDetectives() {
    List<Detective> detectives = IntStream.rangeClosed(1, 4)
        .mapToObj(number -> initializeDetective(number))
        .collect(Collectors.toList());
    return Collections.unmodifiableList(detectives);
  }

  private Detective initializeDetective(final int number) {
    final StartPosition startPosition = startPositionService.zieheFreieStartPosition();
    final Tickets detectiveTickets = startTicketService.getDetectiveTickets();
    final Detective detective = new Detective(number, startPosition, detectiveTickets);
    detectiveService.initialize(startPosition, detectiveTickets);
    // TODO [jdufner, 2018-09-20] When and how will be set the players on the board?
    return detective;
  }

  private Mrx initializeMrx() {
    final StartPosition startPosition = startPositionService.zieheFreieStartPosition();
    final Tickets mrxTickets = startTicketService.getMrxTickets();
    final Mrx mrx = new Mrx(startPosition, mrxTickets);
    mrxService.initialize(startPosition, mrxTickets);
    // TODO [jdufner, 2018-09-20] When and how will be set the players on the board?
    return mrx;
  }

  public void nextLap(final Game spiel) {
    spiel.nextLap();
    Move move = mrxService.nextMove();
//    spiel.getDetektive().forEach((Detective detektiv) -> detektivService.fuehreZugDurch(spiel,
//        detektiv));
//    LOG.debug("Runde {} beendet.", spiel.getCurrentLap());
  }

}
