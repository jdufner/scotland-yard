package de.jdufner.scotland.yard.model.position;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Startposition extends Position {

  private static Integer[] startpositionen = {13, 26, 29, 34, 50, 53, 91, 94, 103, 112, 117,
      132, 138, 131, 155, 174, 197, 198};

  private static List<Integer> freiePositionen = new ArrayList<>(startpositionen.length);

  static {
    freiePositionen.addAll(asList(startpositionen));
  }

  private Startposition(int position) {
    super(position);
  }

  public static Startposition zieheFreiStartposition() {
    return new Startposition(freiePositionen.remove((int) (Math.random() * freiePositionen.size
        ())));
  }

}
