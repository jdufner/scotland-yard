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

package de.jdufner.scotland.yard.gameboard.config;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.impl.factory.GraphDatabaseFacade;
import org.neo4j.server.CommunityBootstrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
@Configuration
@ComponentScan("de.jdufner.scotland.yard.gameboard")
public class Neo4jConfig {

  @Value("${neo4j.database.directory:./neo4j-data}")
  private String databaseDirectoryAsString;

  @Value("${neo4j.config.file:./config/neo4j.conf}")
  private String configFileAsString;

  @Value("${neo4j.dbms.connector.http.address:${neo4j.dbms.connector.http.address:0.0.0.0:7474}}")
  private String dbmsConnectorHttpAddress;

  @Value("${neo4j.dbms.security.auth_enabled:false}")
  private String dbmsSecurityAuthEnable;

  @Value("${neo4j.dbms.directories.import:../src/main/resources}")
  private String dbmsDirectoriesImport;

  @Bean
  public GraphDatabaseService graphDatabaseService() {
    final GraphDatabaseFacade graphDatabaseFacade = bootstrapGraphDatabase();
    return graphDatabaseFacade;
  }

  private GraphDatabaseFacade bootstrapGraphDatabase() {
    final CommunityBootstrapper communityBootstrapper = new CommunityBootstrapper();
    final Map<String, String> properties = new HashMap<>();
    properties.put("dbms.connector.http.address", dbmsConnectorHttpAddress);
    properties.put("dbms.directories.import", dbmsDirectoriesImport);
    properties.put("dbms.security.auth_enabled", dbmsSecurityAuthEnable);
    communityBootstrapper.start(getDatabaseDirectory(), Optional.of(getConfigFile()), properties);
    return communityBootstrapper.getServer().getDatabase()
        .getGraph();
  }

  private File getDatabaseDirectory() {
    return new File(databaseDirectoryAsString);
  }

  private File getConfigFile() {
    return new File(configFileAsString);
  }

}
