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

import de.jdufner.scotland.yard.common.position.Position;
import de.jdufner.scotland.yard.common.ticket.TaxiTicket;
import de.jdufner.scotland.yard.gamecontroller.model.spiel.Game;
import de.jdufner.scotland.yard.gamecontroller.model.spieler.Mrx;
import de.jdufner.scotland.yard.gamecontroller.model.zug.Zug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Dieser Service hat zuviel gemacht: Hier lag das Wissen wie Mr. X erzeugt wird und auch welche Zug
 * er machen soll, aber das sind zwei getrennte Aufgaben. Der Spielleiter gibt Mr. X eine
 * StartPosition und die Tickets. Mr. X darf selbst nur festlegen, wohin er zieht und muss die
 * Tickets dafür abgeben.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
@Deprecated
public class MrXService extends SpielerService<Mrx> {

  private static final Logger LOG = LoggerFactory.getLogger(MrXService.class);

  public MrXService(final BoardService boardService,
                    final StartPositionService startPositionService) {
    super(boardService, startPositionService);
  }

  @Override
  protected Zug ermittleNächstenZug(final Game game, final Mrx spieler) {
    // Welche Verkehrsmittel kann Mrx nutzen?
    final Position position = boardService.findeNachbarAmWeitestenEntferntVonDetektiven();
    return new Zug(position, new TaxiTicket(1));
  }

}
