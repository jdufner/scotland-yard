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

package de.jdufner.scotland.yard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@SpringBootApplication
@EnableNeo4jRepositories()
public class App {

  private final static Logger LOG = LoggerFactory.getLogger(App.class);

  public static void main(final String[] args) {
    SpringApplication.run(App.class, args);
  }

// TODO: Löschen
//  @Bean
//  CommandLineRunner initialize(final PositionRepository positionRepository) {
//    return args -> {
//      positionRepository.deleteAll();
//      Position p = new Position(12345);
//      positionRepository.save(p);
//    };
//  }

}
