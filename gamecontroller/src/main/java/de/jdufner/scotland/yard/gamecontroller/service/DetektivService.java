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

package de.jdufner.scotland.yard.gamecontroller.service;

import de.jdufner.scotland.yard.gamecontroller.model.spiel.Spiel;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Detektiv;
import de.jdufner.scotland.yard.gamecontroller.model.zug.Zug;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Deprecated
@Service
public class DetektivService extends SpielerService<Detektiv> {

  private static final Logger LOG = LoggerFactory.getLogger(DetektivService.class);

  public DetektivService(final SpielbrettService spielbrettService,
                         final StartPositionService startPositionService) {
    super(spielbrettService, startPositionService);
  }

  public List<Detektiv> erzeugeDetektive() {
    final List<Detektiv> detektive = new ArrayList<>();
    IntStream.rangeClosed(1, 4).forEach(i ->
        detektive.add(erzeugeDetektiv(i))
    );
    return Collections.unmodifiableList(detektive);
  }

  private Detektiv erzeugeDetektiv(int nummer) {
    return new Detektiv(startPositionService.zieheFreieStartPosition(), nummer);
  }

  @Override
  public Detektiv erzeugeSpieler() {
    return erzeugeDetektiv(0);
  }

  @Override
  protected Zug ermittleNächstenZug(final Spiel spiel, final Detektiv spieler) {
    if (spiel.getAktuelleRunde() < 3) {
      return new Zug(spielbrettService.findeWegZuUndergroundInAnzahlZuegen(spieler,
          3 - spiel.getAktuelleRunde()), null);
    }

//    return new PositionUndTickets(spielbrettService.findeKuerzestenWegZuMrX(spieler,
//        3 - spiel.getAktuelleRunde()), null);

    return new Zug(spieler.letztePosition(), null);
  }

}
