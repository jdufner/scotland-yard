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

package de.jdufner.scotland.yard.mrx;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.gameboard.service.BoardService;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class ArbitraryMove implements MrxMove {

  private final BoardService boardService;

  private Random random = new Random();

  public ArbitraryMove(final BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public Move nextMove(final MrxGameStatus mrxGameStatus) {
    final List<Move> allPossibleMoves = boardService.findAllPossibleMoves(mrxGameStatus.getCurrentPosition());
    final List<Move> allAllowedMoves = allPossibleMoves.stream()
        .filter(possibleMove -> mrxGameStatus.getTickets().contains(possibleMove.getTicket()))
        .collect(toList());
    if (allAllowedMoves.size() <= 0) {
      throw new RuntimeException(format("No Tickets %s remaining to do a legal move %s!",
          mrxGameStatus.getTickets(), allPossibleMoves));
    }
    final Move move = allAllowedMoves.get(random.nextInt(allPossibleMoves.size()));
    return move;
  }

}
