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

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class SpielerService<T extends Spieler> {

  private static final Logger LOG = LoggerFactory.getLogger(SpielerService.class);

  protected final SpielbrettService spielbrettService;
  protected final StartpositionService startpositionService;

  public SpielerService(final SpielbrettService spielbrettService,
                        final StartpositionService startpositionService) {
    this.spielbrettService = spielbrettService;
    this.startpositionService = startpositionService;
  }

  public abstract T erzeugeSpieler();

  /**
   * Template-Method für die Durchführung eines Zuges.
   *
   * @param spiel
   * @param spieler
   */
  public void fuehreZugDurch(final Spiel spiel, final T spieler) {
    LOG.debug("Führe Zug {} für Spieler {} durch.", spiel.getAktuelleRunde(), spieler);
    PositionUndTickets positionUndTickets = ermittleNächstenZug(spiel, spieler);
    spieler.zieheUndVerbraucheTickets(positionUndTickets);
    spielbrettService.verschiebeSpieler(spieler);
    // MrX soll sich zeigen, wenn es ein relevanter Spielzug ist
  }

  protected abstract PositionUndTickets ermittleNächstenZug(
      final Spiel spiel, final T spieler);

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
