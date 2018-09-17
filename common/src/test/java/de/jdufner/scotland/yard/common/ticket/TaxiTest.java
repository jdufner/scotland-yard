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
 *
 */

package de.jdufner.scotland.yard.common.ticket;

import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public class TaxiTest {

  @Test
  public void whenCompare_expectEquals() {
    // arrange
    int anzahl = 10;
    Taxi taxi1 = new Taxi(anzahl);
    Taxi taxi2 = new Taxi(anzahl);

    // act + assert
    Assertions.assertThat(taxi1).isEqualTo(taxi2);
  }

  @Test
  public void whenCompare_expectNotEquals() {
    // arrange
    Taxi taxi1 = new Taxi(5);
    Taxi taxi2 = new Taxi(10);

    // act + assert
    Assertions.assertThat(taxi1).isNotEqualTo(taxi2);
  }

}