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

import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 0.3
 */
@Service
public class SpielService {

  private static final Logger LOG = LoggerFactory.getLogger(SpielService.class);

  private final Collection<SpielerService> spielerServices;

  public SpielService(final Collection<SpielerService> spielerServices) {
    this.spielerServices = spielerServices;
  }

  public Spiel erzeugeSpiel() {
    LOG.debug("Erzeuge Spiel!");
    final MrX mrX = erzeugeSpieler(MrX.class);
    final List<Detektiv> detektivs = new ArrayList<>();
    detektivs.add(erzeugeSpieler(Detektiv.class));
    detektivs.add(erzeugeSpieler(Detektiv.class));
    detektivs.add(erzeugeSpieler(Detektiv.class));
    detektivs.add(erzeugeSpieler(Detektiv.class));
    Spiel spiel = new Spiel(mrX, Collections.unmodifiableList(detektivs));
    LOG.debug("Spiel erzeugt: {}", spiel);
    return spiel;
  }

  public void naechsteRunde(final Spiel spiel) {
    spiel.naechsteRunde();
    LOG.debug("Spiele Runde: " + spiel.getAktuelleRunde());
    spiel.getSpieler().forEach((Spieler spieler) ->
        getSpielerService(spieler.getClass()).fuehreZugDurch(spiel, spieler));
    LOG.debug("Runde {} beendet.", spiel.getAktuelleRunde());
  }

  private <T extends Spieler> T erzeugeSpieler(final Class<T> spielerType) {
    return (T) getSpielerService(spielerType).erzeugeSpieler();
  }

  private SpielerService getSpielerService(final Class<? extends Spieler> spielerType) {
    return spielerServices.stream()
        .filter(spielerService -> spielerService.getTypeOf().equals(spielerType))
        .findFirst()
        .orElseThrow(AssertionError::new);
  }

}
