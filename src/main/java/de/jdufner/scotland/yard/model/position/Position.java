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
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Position position1 = (Position) o;

    return position == position1.position;
  }

  @Override
  public int hashCode() {
    return position;
  }

  @Override
  public String toString() {
    return "Position: " + String.valueOf(position);
  }
}
