package mcup.core.local;

import mcup.core.Core;
import mcup.core.local.data.Database;


public class Repository {

  public Database data;
  private final Fallback fallback;

  private Database fetchFromDatabase() {
    // TODO
    return null;
  }

  private boolean writeToDatabase(Database data) {
    // TODO
    return false;
  }

  public void fetch() {

    Database fetchedData = fetchFromDatabase();

    if (fetchedData != null) {
      data = fetchedData;
      plugin.getLogger().info("Configuration successfully fetched from database");
      return;
    }

    plugin.getLogger().warning("Database configuration fetch failed");

    fetchedData = fallback.fetchFromConfig();

    if (fetchedData != null) {
      data = fetchedData;
      plugin.getLogger().info("Configuration fallback fetch system operational");
      return;
    }

    plugin.getLogger().warning("Fallback configuration fetch failed");
    plugin.getLogger().warning("CRITICAL: UNABLE TO LOAD CONFIGURATION");
  }

  public void write() {

    boolean saved = false;
    if (writeToDatabase(data)) {
      plugin.getLogger().info("Configuration successfully written to database");
      saved = true;
    }

    else
      plugin.getLogger().warning("Database configuration write failed");


    if (fallback.write_to_config(data)) {
      plugin.getLogger().info("Configuration fallback write system operational");
      saved = true;
    }

    else
      plugin.getLogger().warning("Fallback configuration write failed");


    if (!saved)
      plugin.getLogger().warning("CRITICAL: UNABLE TO SAVE CONFIGURATION");
  }

  private final Core plugin;
  public Repository(Core plugin_) {
    plugin = plugin_;
    fallback = new Fallback(plugin);
  }

}