package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.spieler.MrX;
import org.springframework.stereotype.Service;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class MrXService extends SpielerService<MrX> {

  public MrXService(final SpielbrettService spielbrettService) {
    super(spielbrettService);
  }

  @Override
  public Class<MrX> getSpielerType() {
    return MrX.class;
  }

  @Override
  public void ziehe(final MrX spieler) {

  }

}
