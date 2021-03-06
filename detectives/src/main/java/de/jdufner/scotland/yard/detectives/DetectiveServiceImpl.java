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

package de.jdufner.scotland.yard.detectives;

import static java.lang.String.format;

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.move.Path;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.gameboard.service.BoardService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class DetectiveServiceImpl implements DetectiveService {

  private final BoardService boardService;

  private Map<PlayerInfo, Detective> detectiveMap = new HashMap<>();
  private Random random = new Random();

  public DetectiveServiceImpl(BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void initialize(PlayerInfo playerInfo, Position Position, Tickets tickets) {
    detectiveMap.put(playerInfo, new Detective(playerInfo, Position, tickets));
  }

  @Override
  public Move nextMove(PlayerInfo playerInfo) {
    return arbitraryMove(playerInfo);
  }

  private Move arbitraryMove(PlayerInfo playerInfo) {
    final List<Path> allPossiblePathes = boardService.findAllPathes(detectiveMap.get(playerInfo).getCurrentPosition());
    List<Path> allAllowedPathes = allPossiblePathes.stream()
        .filter(possibleMove -> detectiveMap.get(playerInfo).getTickets().contains(possibleMove.getTicket()))
        .collect(Collectors.toList());
    if (allAllowedPathes.size() <= 0) {
      throw new RuntimeException(format("No Tickets %s remaining for detective %s to do a legal move %s!",
          detectiveMap.get(playerInfo).getTickets(), playerInfo, allPossiblePathes));
    }
    final Path path = allAllowedPathes.get(random.nextInt(allAllowedPathes.size()));
    final Move move = new Move(playerInfo, path);
    detectiveMap.get(playerInfo).moveTo(path.getEnd());
    return move;
  }

  @Override
  public void showMrx(PlayerInfo playerInfo, Position currentPosition) {
  }

}
