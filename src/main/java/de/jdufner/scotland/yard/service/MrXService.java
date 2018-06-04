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

import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.MrX;
import de.jdufner.scotland.yard.model.tickets.Taxi;
import de.jdufner.scotland.yard.model.zug.Zug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class MrXService extends SpielerService<MrX> {

  private static final Logger LOG = LoggerFactory.getLogger(MrXService.class);

  public MrXService(final SpielbrettService spielbrettService,
                    final StartpositionService startpositionService) {
    super(spielbrettService, startpositionService);
  }

  @Override
  public MrX erzeugeSpieler() {
    return erzeugeMrX();
  }

  public MrX erzeugeMrX() {
    return new MrX(startpositionService.zieheFreieStartposition());
  }

  @Override
  protected Zug ermittleNächstenZug(final Spiel spiel, final MrX spieler) {
    final Position position = spielbrettService.findeNachbarAmWeitestenEntferntVonDetektiven();
    return new Zug(position, new Taxi(1));
  }

}
