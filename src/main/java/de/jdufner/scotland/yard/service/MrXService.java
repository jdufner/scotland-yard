package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.position.Position;
import de.jdufner.scotland.yard.model.spieler.MrX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
  public Class<MrX> getSpielerType() {
    return MrX.class;
  }

  @Override
  public Position ziehe(final MrX mrX) {
    final Position position = spielbrettService.findeNachbarAmWeitestenEntferntVonDetektiven();
    mrX.zieheAuf(position);
    spielbrettService.verschiebeSpieler(mrX);
    LOG.debug("Ziehe Mr. X auf Position: " + position);
    return position;
  }

  @Override
  protected PositionUndTickets ermittleNächstenZug() {
    return null;
  }
}
