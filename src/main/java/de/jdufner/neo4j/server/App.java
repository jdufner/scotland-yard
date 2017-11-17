package de.jdufner.neo4j.server;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.server.CommunityBootstrapper;
import org.neo4j.server.NeoServer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class App {

  private static File databaseDirectory = new File("./neo4j-data");
  private static File configFile = new File("./config/neo4j.conf");

  public static void main(String[] args) {
    CommunityBootstrapper communityBootstrapper = new CommunityBootstrapper();
    Map<String, String> properties = new HashMap<>();
    properties.put("bolt.enabled", "false");
    properties.put("dbms.connector.bolt.enabled", "false");
    properties.put("dbms.connector.http.address", "0.0.0.0:7474");
    communityBootstrapper.start(databaseDirectory, Optional.of(configFile), properties);
    NeoServer neoServer = communityBootstrapper.getServer();
    registerShutdownHook(neoServer.getDatabase().getGraph());
  }

  private static void registerShutdownHook(final GraphDatabaseService graphDb) {
    // Registers a shutdown hook for the Neo4j instance so that it
    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
    // running application).
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        graphDb.shutdown();
      }
    });
  }

}
