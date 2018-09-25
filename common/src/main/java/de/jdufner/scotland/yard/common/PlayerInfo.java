/*
 * Scotland Yard is a simulation of the board game.
 * Copyright (C) 2008-2018  Juergen Dufner
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jdufner.scotland.yard.common;

import java.util.Objects;
import java.util.UUID;

public class PlayerInfo {

  private final Type type;
  private final Id id;

  public PlayerInfo(final Type type, final Id id) {
    this.type = type;
    this.id = id;
  }

  public Type getType() {
    return type;
  }

  public Id getId() {
    return id;
  }

  public enum Type {MRX, DETECTIVE;}

  public static class Id {

    private String uuid;

    public Id(String uuid) {
      this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Id id = (Id) o;
      return Objects.equals(uuid, id.uuid);
    }

    @Override
    public int hashCode() {
      return Objects.hash(uuid);
    }

    @Override
    public String toString() {
      return uuid;
    }
  }

  public static class Builder {

    public static Builder newMrx() {
      return new Builder()
          .withType(Type.MRX)
          .withId(UUID.randomUUID().toString());
    }

    public static Builder newDetective() {
      return new Builder()
          .withType(Type.DETECTIVE)
          .withId(UUID.randomUUID().toString());
    }

    private Type type;
    private Id id;

    public Builder() {
    }

    public Builder withType(final Type type) {
      this.type = type;
      return this;
    }

    public Builder withId(final String idAsString) {
      this.id = new Id(idAsString);
      return this;
    }

    public PlayerInfo build() {
      return new PlayerInfo(type, id);
    }

  }
}
