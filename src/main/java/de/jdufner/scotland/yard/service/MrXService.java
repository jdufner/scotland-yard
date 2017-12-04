package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.MrX;
import de.jdufner.scotland.yard.model.tickets.Taxi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class MrXService extends SpielerService<MrX> {

  private static final Logger LOG = LoggerFactory.getLogger(MrXService.class);

  public MrXService(final SpielbrettService spielbrettService) {
    super(spielbrettService);
  }

  @Override
  protected PositionUndTickets ermittleNächstenZug(final Spiel spiel, final MrX spieler) {
    final Position position = spielbrettService.findeNachbarAmWeitestenEntferntVonDetektiven();
    return new PositionUndTickets(position, singletonList(new Taxi(1)));
  }

}
