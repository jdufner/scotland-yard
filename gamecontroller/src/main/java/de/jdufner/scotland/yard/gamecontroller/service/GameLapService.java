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
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Detective;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Mrx;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static java.lang.String.format;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class GameLapService {

  // TODO [jdufner, 2018-09-19] Is this the right place?
  private static final int[] auftauchen = {3, 8, 13, 18};

  private static final Logger LOG = LoggerFactory.getLogger(GameLapService.class);

  private final MrxService mrxService;
  private final DetectiveService detectiveService;
  private final SpielbrettService spielbrettService;

  public GameLapService(final MrxService mrxService,
                        final DetectiveService detectiveService,
                        final SpielbrettService spielbrettService) {
    this.mrxService = mrxService;
    this.detectiveService = detectiveService;
    this.spielbrettService = spielbrettService;
  }

  public void nextLap(final Game game) {
    game.nextLap();
    moveMrx(game);
    moveDetectives(game);
//    LOG.debug("Runde {} beendet.", game.getCurrentLap());
  }

  private void moveMrx(final Game game) {
    Move move = mrxService.nextMove();
    checkStartPosition(move, game.getMrx());
    checkIsMoveValid(move);
    checkHasEnoughTickets(move, game.getMrx());
    game.getMrx().move(move);
  }

  private void moveDetectives(Game game) {
    game.getDetektives().forEach(detektiv -> moveDetective(game, detektiv));
  }

  private void moveDetective(final Game game, final Detective detective) {
    Move move = doMoveDetective(game, detective);
    checkStartPosition(move, detective);
    checkIsMoveValid(move);
    checkHasEnoughTickets(move, detective);
    detective.move(move);
    handOverTicket(move, game.getMrx());
  }

  private Move doMoveDetective(Game game, Detective detective) {
    Move move;
    if (Arrays.asList(auftauchen).contains(game.getCurrentLap())) {
      move = detectiveService.nextMove(detective.getPlayerInfo(), game.getMrx().getCurrentPosition());
    } else {
      move = detectiveService.nextMove(detective.getPlayerInfo());
    }
    return move;
  }

  private void checkStartPosition(Move move, Player player) {
    if (!player.getCurrentPosition().equals(move.getStart())) {
      throw new RuntimeException(format("Move of Mr. X starts not the from position. " +
          "Expected position=%s, but was=%s", player.getCurrentPosition(), move.getStart()));
    }
  }

  private void checkIsMoveValid(Move move) {
    spielbrettService.isMoveValid(move);
  }

  private void checkHasEnoughTickets(Move move, Player player) {
    player.getTickets().contains(move.getTicket());
  }

  private void handOverTicket(Move move, Mrx mrx) {
    mrx.addTicket(move.getTicket());
  }

}
