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

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;

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
  }

  private void moveMrx(final Game game) {
    Move move = mrxService.nextMove();
    checkStartPosition(move, game.getMrx());
    checkIsMoveValid(move);
    checkHasEnoughTickets(move, game.getMrx());
    game.getMrx().move(move);
    checkHasMrxBeenMovedToADetective(game);
  }

  private void moveDetectives(Game game) {
    game.getDetectives().forEach(detektiv -> moveDetective(game, detektiv));
  }

  private void moveDetective(final Game game, final Detective detective) {
    Move move = doMoveDetective(game, detective);
    checkStartPosition(move, detective);
    checkIsMoveValid(move);
    checkEndPosition(move, game.getDetectives());
    checkHasEnoughTickets(move, detective);
    detective.move(move);
    handOverTicket(move, game.getMrx());
  }

  private Move doMoveDetective(Game game, Detective detective) {
    if (asList(auftauchen).contains(game.getCurrentLap())) {
      detectiveService.showMrx(detective.getPlayerInfo(), game.getMrx().getCurrentPosition());
    }
    return detectiveService.nextMove(detective.getPlayerInfo());
  }

  private void checkStartPosition(Move move, Player player) {
    if (!player.getCurrentPosition().equals(move.getStart())) {
      throw new RuntimeException(format("Move of player %s starts not the from its position. " +
          "Expected position=%s, but was=%s", player.getPlayerInfo(), player.getCurrentPosition(), move.getStart()));
    }
  }

  private void checkIsMoveValid(Move move) {
    if (!spielbrettService.isMoveValid(move)) {
      throw new RuntimeException(format("It isn't allowed to move from %s to %s by %s",
          move.getStart(), move.getEnd(), move.getTicket()));
    }
  }

  private void checkEndPosition(Move move, List<Detective> detectives) {
    detectives.forEach(detective -> checkEndPosition(move, detective));
  }

  private void checkEndPosition(Move move, Player player) {
    if (player.getCurrentPosition().equals(move.getEnd())) {
      throw new RuntimeException(format("Move of current player isn't allowed, arrival field %s is in use by %s.",
          move.getEnd(), player.getPlayerInfo()));
    }
  }

  private void checkHasEnoughTickets(Move move, Player player) {
    if (!player.getTickets().contains(move.getTicket())) {
      throw new RuntimeException(format("Player %s hasn't enough Tickets %s",
          player.getPlayerInfo(), move.getTicket()));
    }
  }

  private void handOverTicket(Move move, Mrx mrx) {
    mrx.addTicket(move.getTicket());
  }

  private void checkHasMrxBeenMovedToADetective(Game game) {
    if (game.haveDetectivesCatchedMrx()) {
      throw new RuntimeException(format("Illegal Move, %s must not move to a current position of a %s",
          game.getMrx(), game.getDetectives()));
    }
  }

}
