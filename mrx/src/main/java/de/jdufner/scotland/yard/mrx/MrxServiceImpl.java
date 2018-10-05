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
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class MrxServiceImpl implements MrxService {

  private final MrxMove mrxMove;

  private MrxGameStatus mrxGameStatus;

  public MrxServiceImpl(final MrxMove mrxMove) {
    this.mrxMove = mrxMove;
  }

  @Override
  public void initialize(final PlayerInfo playerInfo, final Position position, final Tickets tickets) {
    this.mrxGameStatus = new MrxGameStatus(playerInfo, position, tickets);
  }

  @Override
  public void setDetectivesPosition(final PlayerInfo playerInfo, final Position position) {
    mrxGameStatus.setDetectivesPosition(playerInfo, position);
  }

  @Override
  public void giveTicket(final Ticket ticket) {
    mrxGameStatus.giveTicket(ticket);
  }

  @Override
  public Move nextMove() {
    Move move = mrxMove.nextMove(mrxGameStatus);
    mrxGameStatus.doMove(move);
    return move;
  }

}
