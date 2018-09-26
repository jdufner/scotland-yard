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

package de.jdufner.scotland.yard.common;


import de.jdufner.scotland.yard.common.move.Move;
import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.position.StartPosition;

/**
 * This interface has to be implemented by the gamelogic of the detectives.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
public interface DetectiveService {

  void initialize(final PlayerInfo playerInfo, final StartPosition startPosition, final Tickets tickets);

  Move nextMove(PlayerInfo playerInfo);

  // TODO [jdufner, 2018-09-26] Create a separate method to set the position of Mr. X.
  @Deprecated
  Move nextMove(PlayerInfo playerInfo, Position positionOfMrx);
}
