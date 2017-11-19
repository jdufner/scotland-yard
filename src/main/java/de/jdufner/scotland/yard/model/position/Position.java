package de.jdufner.scotland.yard.model.position;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class Position {

  final int position;

  public Position(int position) {
    this.position = position;
  }

  public int getPosition() {
    return position;
  }

  @Override
  public String toString() {
    return "Position: " + String.valueOf(position);
  }
}
