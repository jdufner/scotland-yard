package de.jdufner.scotland.yard.service;

import de.jdufner.scotland.yard.model.position.Startposition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Der Service ist zufallsgesteuert.
 *
 * @author Jürgen Dufner
 * @since 1.0
 */
@Service
public class StartpositionService {

  private static Integer[] startpositionen = {13, 26, 29, 34, 50, 53, 91, 94, 103, 112, 117,
      132, 138, 131, 155, 174, 197, 198};

  private static List<Integer> freiePositionen = new ArrayList<>(startpositionen.length);

  static {
    freiePositionen.addAll(asList(startpositionen));
  }

  public Startposition zieheFreieStartposition() {
    assert freiePositionen.size() > 0 :
        "Es existiert keine freie Startposition mehr! Es können maximal " + startpositionen.length +
            " Startpositionen gezogen werden.";
    return new Startposition(
        freiePositionen.remove((int) (Math.random() * freiePositionen.size())));
  }

}
