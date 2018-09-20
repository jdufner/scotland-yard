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

package de.jdufner.scotland.yard.gamecontroller.model.spiel;

import de.jdufner.scotland.yard.gamecontroller.model.spieler.Detective;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Mrx;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Player;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Das Game hält Status der Figuren, der Runden etc. Die Transitionen werden von einem Service
 * ausgelöst.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Game {

  private static final Logger LOG = LoggerFactory.getLogger(Game.class);

  private static final int TOTAL_LAPS = 21;

  private final Mrx mrX;
  private final List<Detective> detektive;
  private final List<Player> players = new ArrayList<>();

  private int currentLap;

  private boolean finished = false;

  public Game(final Mrx mrX, final List<Detective> detektive) {
    this.mrX = mrX;
    this.detektive = detektive;
    players.add(mrX);
    players.addAll(detektive);
  }

  public Mrx getMrX() {
    return mrX;
  }

  public List<Detective> getDetektive() {
    return detektive;
  }

  public List<Player> getSpieler() {
    return players;
  }

  public int naechsteRunde() {
    return currentLap++;
  }

  public int getCurrentLap() {
    return currentLap;
  }

  private boolean habenDetektiveMrXGefangen() {
    for (Detective detective : detektive) {
      if (detective.currentPosition().equals(mrX.currentPosition())) {
        return true;
      }
    }
    return false;
  }

  public boolean isFinished() {
    return sindAlleRundenGespielt() || habenDetektiveMrXGefangen();
  }

  private boolean sindAlleRundenGespielt() {
    return currentLap >= TOTAL_LAPS;
  }

  @Override
  public String toString() {
    return "Player: {" + players.toString() + "}";
  }

}
