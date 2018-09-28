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

package de.jdufner.scotland.yard.gamecontroller.service;

import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Player;
import de.jdufner.scotland.yard.gamecontroller.model.zug.Zug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class SpielerService<T extends Player> {

  private static final Logger LOG = LoggerFactory.getLogger(SpielerService.class);

  protected final BoardService boardService;
  protected final StartPositionService startPositionService;

  public SpielerService(final BoardService boardService,
                        final StartPositionService startPositionService) {
    this.boardService = boardService;
    this.startPositionService = startPositionService;
  }

  /**
   * Template-Method für die Durchführung eines Zuges.
   *
   * @param game
   * @param spieler
   */
  public void fuehreZugDurch(final Game game, final T spieler) {
    LOG.debug("Führe Zug {} für Player {} durch.", game.getCurrentLap(), spieler);
    Zug zug = ermittleNächstenZug(game, spieler);
    spieler.ziehe(zug);
    boardService.verschiebeSpieler(spieler);
    // Mrx soll sich zeigen, wenn es ein relevanter Spielzug ist
  }

  protected abstract Zug ermittleNächstenZug(
      final Game game, final T spieler);

  Class getTypeOf() {
    final Type genericInterface = this.getClass().getGenericSuperclass();
    if (genericInterface instanceof ParameterizedType) {
      final ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
      if (parameterizedType.getRawType().equals(SpielerService.class)) {
        return (Class) parameterizedType.getActualTypeArguments()[0];
      }
    }
    throw new RuntimeException(
        "Implements " + getClass().getSimpleName() + " a generic interface ?");
  }

}
