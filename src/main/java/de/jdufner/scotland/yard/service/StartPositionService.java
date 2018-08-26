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

package de.jdufner.scotland.yard.service;

import static java.util.Arrays.asList;

import de.jdufner.scotland.yard.model.position.StartPosition;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Der Service ist zufallsgesteuert.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class StartPositionService {

  private static Integer[] startpositionen = {13, 26, 29, 34, 50, 53, 91, 94, 103, 112, 117,
      132, 138, 131, 155, 174, 197, 198};

  private static List<Integer> freiePositionen = new ArrayList<>(startpositionen.length);

  // So oder besser @PostConstruct?
  static {
    freiePositionen.addAll(asList(startpositionen));
  }

  public StartPosition zieheFreieStartPosition() {
    assert freiePositionen.size() > 0 :
        "Es existiert keine freie StartPosition mehr! Es können maximal " + startpositionen.length +
            " Startpositionen gezogen werden.";
    return new StartPosition(
        freiePositionen.remove((int) (Math.random() * freiePositionen.size())));
  }

}
