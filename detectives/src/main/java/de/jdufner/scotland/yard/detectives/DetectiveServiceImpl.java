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

package de.jdufner.scotland.yard.detectives;

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.Tickets;
import de.jdufner.scotland.yard.common.position.StartPosition;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class DetectiveServiceImpl implements DetectiveService {

  @Override
  public void initialize(StartPosition startPosition, Tickets tickets) {

  }
}
