package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.PositionUndTickets;
import de.jdufner.scotland.yard.model.spieler.Spieler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public abstract class SpielerService<T extends Spieler> {

  protected final SpielbrettService spielbrettService;

  public SpielerService(final SpielbrettService spielbrettService) {
    this.spielbrettService = spielbrettService;
  }

  /**
   * Template-Method für die Durchführung eines Zuges.
   *
   * @param spieler
   */
  public void fuehreZugDurch(final T spieler) {
    PositionUndTickets positionUndTickets = ermittleNächstenZug();
    spieler.zieheUndVerbraucheTickets(positionUndTickets);
    spielbrettService.verschiebeSpieler(spieler);
  }

  protected abstract PositionUndTickets ermittleNächstenZug();

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
