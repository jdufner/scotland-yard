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
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class DetektivService extends SpielerService<Detektiv> {

  private static final Logger LOG = LoggerFactory.getLogger(DetektivService.class);

  private int detektivNummer = 0;

  public DetektivService(final SpielbrettService spielbrettService,
                         final StartpositionService startpositionService) {
    super(spielbrettService, startpositionService);
  }

  @Override
  public Detektiv erzeugeSpieler() {
    return new Detektiv(startpositionService.zieheFreieStartposition(), detektivNummer++);
  }

  @Override
  protected PositionUndTickets ermittleNächstenZug(final Spiel spiel, final Detektiv spieler) {
    if (spiel.getAktuelleRunde() < 3) {
      return new PositionUndTickets(spielbrettService.findeWegZuUndergroundInAnzahlZuegen(spieler,
          3 - spiel.getAktuelleRunde()), null);
    }

//    return new PositionUndTickets(spielbrettService.findeKuerzestenWegZuMrX(spieler,
//        3 - spiel.getAktuelleRunde()), null);

    return new PositionUndTickets(spieler.letztePosition(), null);
  }

}
