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

package de.jdufner.scotland.yard.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.impl.factory.GraphDatabaseFacade;
import org.neo4j.server.CommunityBootstrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Configuration
public class Neo4jConfig {

  private static File databaseDirectory = new File("./neo4j-data");
  private static File configFile = new File("./config/neo4j.conf");

  @Bean
  public GraphDatabaseService graphDatabaseService() {
    final GraphDatabaseFacade graphDatabaseFacade = bootstrapGraphDatabase();
    return graphDatabaseFacade;
  }

  private GraphDatabaseFacade bootstrapGraphDatabase() {
    final CommunityBootstrapper communityBootstrapper = new CommunityBootstrapper();
    final Map<String, String> properties = new HashMap<>();
    properties.put("dbms.connector.http.address", "0.0.0.0:7474");
    properties.put("dbms.security.auth_enabled", "false");
    properties.put("dbms.directories.import", "../src/main/resources");
    communityBootstrapper.start(databaseDirectory, Optional.of(configFile), properties);
    return communityBootstrapper.getServer().getDatabase()
        .getGraph();
  }

}
