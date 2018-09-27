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

package de.jdufner.scotland.yard.gamecontroller.model.spiel;

import de.jdufner.scotland.yard.gamecontroller.model.spieler.Detective;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Mrx;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Player;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Game represents the status of the board.
 * <p>
 * It maintain the status of each {@link Player}, count the laps, checks the if the move was proper
 * and hands over the tickets from the {@link Detective} to {@link Mrx}. It also check after each
 * move whether the game is finished, that imply that all laps are over or one detective is on the
 * same position as Mr. X.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Game {

  private static final Logger LOG = LoggerFactory.getLogger(Game.class);

  private static final int TOTAL_LAPS = 21;

  private final Mrx mrx;
  private final List<Detective> detektives;
  private final List<Player> players = new ArrayList<>();

  private int currentLap;

  public Game(final Mrx mrx, final List<Detective> detektives) {
    this.mrx = mrx;
    this.detektives = detektives;
    players.add(mrx);
    players.addAll(detektives);
  }

  public List<Player> getPlayers() {
    return players;
  }

  public int nextLap() {
    return currentLap++;
  }

  public int getCurrentLap() {
    return currentLap;
  }

  public boolean haveDetectivesCatchedMrx() {
    return detektives.stream().anyMatch(detective ->
        detective.getCurrentPosition().equals(mrx.getCurrentPosition())
    );
  }

  public boolean isFinished() {
    return areAllLapsDone() || haveDetectivesCatchedMrx();
  }

  private boolean areAllLapsDone() {
    return currentLap >= TOTAL_LAPS;
  }

  @Override
  public String toString() {
    return "Player: {" + players.toString() + "}";
  }

  public Mrx getMrx() {
    return mrx;
  }

  public List<Detective> getDetectives() {
    return detektives;
  }

  public static class Builder {

    public static Builder newGameWithOneDetective() {
      return new Builder()
          .withCurrentLap(0)
          .withMrx(Mrx.Builder.defaultMrx().build())
          .withDetective(Detective.Builder.defaultDetective().withNummer(1).withStartpositionAsInt(26).build());
    }

    public static Builder newGameWithFourDetectives() {
      return new Builder()
          .withCurrentLap(0)
          .withMrx(Mrx.Builder.defaultMrx().build())
          .withDetective(Detective.Builder.defaultDetective().withNummer(1).withStartpositionAsInt(26).build())
          .withDetective(Detective.Builder.defaultDetective().withNummer(2).withStartpositionAsInt(29).build())
          .withDetective(Detective.Builder.defaultDetective().withNummer(3).withStartpositionAsInt(34).build())
          .withDetective(Detective.Builder.defaultDetective().withNummer(4).withStartpositionAsInt(50).build());
    }

    private Mrx mrx;
    private List<Detective> detectives = new LinkedList<>();
    private int currentLap = 1;

    public Builder() {
    }

    public Builder withCurrentLap(int currentLap) {
      this.currentLap = currentLap;
      return this;
    }

    public Builder withMrx(Mrx mrx) {
      this.mrx = mrx;
      return this;
    }

    public Builder withDetective(Detective detective) {
      if (this.detectives == null) {
        this.detectives = new LinkedList<>();
      }
      this.detectives.add(detective);
      return this;
    }

    public Game build() {
      Game game = new Game(mrx, detectives);
      game.currentLap = currentLap;
      return game;
    }

  }

}
