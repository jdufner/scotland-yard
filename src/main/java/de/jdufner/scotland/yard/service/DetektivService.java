package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.spieler.Detektiv;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class DetektivService extends SpielerService<Detektiv> {

  public DetektivService(final SpielbrettService spielbrettService) {
    super(spielbrettService);
  }

  @Override
  public Class<Detektiv> getSpielerType() {
    return Detektiv.class;
  }

  @Override
  public void ziehe(final Detektiv spieler) {

  }

}
