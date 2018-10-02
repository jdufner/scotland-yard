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

import de.jdufner.scotland.yard.common.MrxService;
import de.jdufner.scotland.yard.common.PlayerInfo;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.Ticket;
import de.jdufner.scotland.yard.gameboard.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class MrxServiceImpl implements MrxService {

  private final BoardService boardService;

  private PlayerInfo playerInfo;
  private List<Position> track = new LinkedList<>();
  private Tickets tickets;
  private Map<PlayerInfo, Collection<Position>> detectivesPosition = new HashMap<>();

  private Random random = new Random();

  public MrxServiceImpl(final BoardService boardService) {
    this.boardService = boardService;
  }

  @Override
  public void initialize(final PlayerInfo playerInfo, final Position position, final Tickets tickets) {
    this.playerInfo = playerInfo;
    track.add(position);
    this.tickets = tickets;
  }

  @Override
  public Move nextMove() {
    return arbitraryMove();
  }

  private Move arbitraryMove() {
    final List<Move> allPossibleMoves = boardService.findAllPossibleMoves(getCurrentPosition());
    final List<Move> allAllowedMoves = allPossibleMoves.stream()
        .filter(possibleMove -> tickets.contains(possibleMove.getTicket()))
        .collect(toList());
    if (allAllowedMoves.size() <= 0) {
      throw new RuntimeException(format("No Tickets %s remaining to do a legal move %s!",
          tickets, allPossibleMoves));
    }
    final Move move = allAllowedMoves.get(random.nextInt(allPossibleMoves.size()));
    track.add(move.getEnd());
    return move;
  }

  @Override
  public void giveTicket(final Ticket ticket) {
    tickets.add(ticket);
  }

  @Override
  public void setDetectivesPosition(final PlayerInfo playerInfo, final Position position) {
    detectivesPosition.computeIfAbsent(playerInfo, playerInfo1 -> new LinkedList<>()).add(position);
  }

  private Position getCurrentPosition() {
    return track.get(track.size() - 1);
  }

}
