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

import de.jdufner.scotland.yard.common.DetectiveService;
import de.jdufner.scotland.yard.common.MrxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Der {@link SpielService} erzeugt die Spieler, fragt sie nach ihren Zügen und prüft, ob es ein
 * erlaubter Zug ist.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class SpielService {

  private static final Logger LOG = LoggerFactory.getLogger(SpielService.class);

  private final StartPositionService startPositionService;
  private final StartTicketService startTicketService;
  private final MrxService mrxService;
  private final DetectiveService detectiveService;

  public SpielService(final StartPositionService startPositionService, final StartTicketService startTicketService, final MrxService mrxService, final DetectiveService detectiveService) {
    this.startPositionService = startPositionService;
    this.startTicketService = startTicketService;
    this.mrxService = mrxService;
    this.detectiveService = detectiveService;
  }

  public void erzeugeSpiel() {
    mrxService.initialize(startPositionService.zieheFreieStartPosition(), startTicketService.getMrxTickets());
    detectiveService.initialize(startPositionService.zieheFreieStartPosition(), startTicketService.getDetectiveTickets());
  }

//  public Spiel erzeugeSpiel() {
//    final MrX mrX = mrXService.erzeugeMrX();
//    final List<Detektiv> detektive = detektivService.erzeugeDetektive();
//    Spiel spiel = new Spiel(mrX, detektive);
//    return spiel;
//  }

//  public void naechsteRunde(final Spiel spiel) {
//    spiel.naechsteRunde();
//    mrXService.fuehreZugDurch(spiel, spiel.getMrX());
//    spiel.getDetektive().forEach((Detektiv detektiv) -> detektivService.fuehreZugDurch(spiel,
//        detektiv));
//    LOG.debug("Runde {} beendet.", spiel.getAktuelleRunde());
//  }

}
