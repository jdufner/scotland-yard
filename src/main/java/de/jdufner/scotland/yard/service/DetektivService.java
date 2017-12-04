package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
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

  public DetektivService(final SpielbrettService spielbrettService) {
    super(spielbrettService);
  }

  @Override
  protected PositionUndTickets ermittleNächstenZug() {
    return null;
  }

}
