package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class SpielerService<T extends Spieler> {

  private static final Logger LOG = LoggerFactory.getLogger(SpielerService.class);

  protected final SpielbrettService spielbrettService;
  protected final StartpositionService startpositionService;

  public SpielerService(final SpielbrettService spielbrettService,
                        final StartpositionService startpositionService) {
    this.spielbrettService = spielbrettService;
    this.startpositionService = startpositionService;
  }

  public abstract T erzeugeSpieler();

  /**
   * Template-Method für die Durchführung eines Zuges.
   *
   * @param spiel
   * @param spieler
   */
  public void fuehreZugDurch(final Spiel spiel, final T spieler) {
    LOG.debug("Führe Zug {} für Spieler {} durch.", spiel.getAktuelleRunde(), spieler);
    PositionUndTickets positionUndTickets = ermittleNächstenZug(spiel, spieler);
    spieler.zieheUndVerbraucheTickets(positionUndTickets);
    spielbrettService.verschiebeSpieler(spieler);
    // MrX soll sich zeigen, wenn es ein relevanter Spielzug ist
  }

  protected abstract PositionUndTickets ermittleNächstenZug(
      final Spiel spiel, final T spieler);

  Class getTypeOf() {
    final Type genericInterface = this.getClass().getGenericSuperclass();
    if (genericInterface instanceof ParameterizedType) {
      final ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
      if (parameterizedType.getRawType().equals(SpielerService.class)) {
        return (Class) parameterizedType.getActualTypeArguments()[0];
      }
    }
    throw new RuntimeException(
        "Implements " + getClass().getSimpleName() + " a generic interface ?");
  }

}
