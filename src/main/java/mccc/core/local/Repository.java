package mccc.core.local;

import mccc.core.Core;
import mccc.core.local.data.Database;


public class Repository {

  public Database data;
  private final Fallback fallback = new Fallback();

  private Database fetch_from_database() {
    // TODO
    return null;
  }

  private boolean write_to_database(Database data) {
    // TODO
    return false;
  }

  public void fetch() {

    Database fetched_data = fetch_from_database();

    if (fetched_data != null) {
      data = fetched_data;
      plugin.getLogger().info("Configuration successfully fetched from database");
      return;
    }

    plugin.getLogger().warning("Database configuration fetch failed");

    fetched_data = fallback.fetch_from_config();

    if (fetched_data != null) {
      data = fetched_data;
      plugin.getLogger().info("Configuration fallback fetch system operational");
      return;
    }

    plugin.getLogger().warning("Fallback configuration fetch failed");
    plugin.getLogger().warning("CRITICAL: UNABLE TO LOAD CONFIGURATION");
  }

  public void write() {

    boolean saved = false;
    if (write_to_database(data)) {
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
  }

}