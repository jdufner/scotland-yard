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

package de.jdufner.scotland.yard.gamecontroller.service;

import de.jdufner.scotland.yard.common.PlayerInfo;
import org.junit.Test;

import static de.jdufner.scotland.yard.common.PlayerInfo.Type.DETECTIVE;
import static de.jdufner.scotland.yard.common.PlayerInfo.Type.MRX;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerInfoServiceTest {

  @Test
  public void whenGetMrxPlayerInfo_expectOfTypeMrxAndUniqueId() {
    // arrange
    PlayerInfoService playerInfoService = new PlayerInfoService();

    // act
    PlayerInfo playerInfo = playerInfoService.getMrxPlayerInfo();

    // assert
    assertThat(playerInfo.getType()).isEqualTo(MRX);
    assertThat(playerInfo.getId()).isNotNull();
  }

  @Test
  public void whenGetDetectivePlayerInfo_expectOfTypeDetectiveAndUniqueId() {
    // arrange
    PlayerInfoService playerInfoService = new PlayerInfoService();

    // act
    PlayerInfo playerInfo = playerInfoService.getDetectivePlayerInfo();

    // assert
    assertThat(playerInfo.getType()).isEqualTo(DETECTIVE);
    assertThat(playerInfo.getId()).isNotNull();
  }

}