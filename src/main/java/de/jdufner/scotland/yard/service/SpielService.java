package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.spiel.Spiel;
import de.jdufner.scotland.yard.model.spieler.Detektiv;
import de.jdufner.scotland.yard.model.spieler.MrX;
import de.jdufner.scotland.yard.model.spieler.Spieler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Jürgen Dufner
 * @since 0.3
 */
@Service
public class SpielService {

  private static final Logger LOG = LoggerFactory.getLogger(SpielService.class);

  private final StartpositionService startpositionService;
  private final Collection<SpielerService> spielerServices;

  public SpielService(final StartpositionService startpositionService, final
  Collection<SpielerService> spielerServices) {
    this.startpositionService = startpositionService;
    this.spielerServices = spielerServices;
  }

  public Spiel erzeugeSpiel() {
    final MrX mrX = new MrX(startpositionService.zieheFreieStartposition());
    final List<Detektiv> detektivs = new ArrayList<>();
    detektivs.add(new Detektiv(startpositionService.zieheFreieStartposition()));
    detektivs.add(new Detektiv(startpositionService.zieheFreieStartposition()));
    detektivs.add(new Detektiv(startpositionService.zieheFreieStartposition()));
    detektivs.add(new Detektiv(startpositionService.zieheFreieStartposition()));
    Spiel spiel = new Spiel(mrX, Collections.unmodifiableList(detektivs));
    LOG.debug("Spiel erzeugt: {}", spiel);
    return spiel;
  }

  public void naechsteRunde(final Spiel spiel) {
    spiel.naechsteRunde();
    LOG.debug("Spiele Runde: " + spiel.getAktuelleRunde());
    spiel.getSpieler().forEach((Spieler spieler) -> {
      SpielerService service = spielerServices.stream().filter(spielerService ->
          spielerService.getSpielerType().equals(spieler.getClass())).findFirst().get();
      service.ziehe(spieler);
    });
  }

}
