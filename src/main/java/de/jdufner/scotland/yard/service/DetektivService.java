package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.position.Position;
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
  public Class<Detektiv> getSpielerType() {
    return Detektiv.class;
  }

  @Override
  public Position ziehe(final Detektiv detektiv) {
    LOG.debug("Detektive bleiben erstmal auf ihrer Position: " + detektiv.letztePosition());
    return detektiv.letztePosition();
  }

}
