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

package de.jdufner.scotland.yard.mrx;

import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.gameboard.service.BoardService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class LargestDistanceToDetectivesMove implements MrxMove {

  private final BoardService boardService;

  public LargestDistanceToDetectivesMove(final BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public Move nextMove(MrxGameStatus mrxGameStatus) {
    boardService.findPositionsNextToMrxFarAwayFromDetectives(mrxGameStatus.getMrxPosition(), mrxGameStatus.getDetectivesPosition());
    return null;
  }

}
