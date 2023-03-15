package mccc.core.local;

import mccc.core.Core;
import mccc.core.local.data.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Repository {

  public LinkedHashMap<String, Team> teams;
  private Fallback fallback = new Fallback();

  private List<Team> fetch_from_database() {
    // TODO
    return null;
  }

  private boolean write_to_database(List<Team> data) {
    // TODO
    return false;
  }

  public void fetch() {

    List<Team> data;

    data = fetch_from_database();

    if (data != null) {
      teams = list_to_map(data);
      plugin.getLogger().info("Configuration successfully fetched from database");
      return;
    }

    plugin.getLogger().warning("Database configuration fetch failed");

    data = fallback.fetch_from_config();

    if (data != null) {
      teams = list_to_map(data);
      plugin.getLogger().info("Configuration fallback fetch system operational");
      return;
    }

    plugin.getLogger().warning("Fallback configuration fetch failed");
    plugin.getLogger().warning("CRITICAL: UNABLE TO LOAD CONFIGURATION");
  }

  public void write() {

    boolean saved = false;
    if (write_to_database(map_to_list(teams))) {
      plugin.getLogger().info("Configuration successfully written to database");
      saved = true;
    }

    else
      plugin.getLogger().warning("Database configuration write failed");


    if (fallback.write_to_config(map_to_list(teams))) {
      plugin.getLogger().info("Configuration fallback write system operational");
      saved = true;
    }

    else
      plugin.getLogger().warning("Fallback configuration write failed");


    if (!saved)
      plugin.getLogger().warning("CRITICAL: UNABLE TO SAVE CONFIGURATION");
  }

  private LinkedHashMap<String, Team> list_to_map(List<Team> list) {
    LinkedHashMap<String, Team> return_value =  new LinkedHashMap<>();

    for (Team team : list) {
      return_value.put(team.name, team);
    }

    return return_value;
  }

  private List<Team> map_to_list(LinkedHashMap<String, Team> data) {

    List<Team> return_value = new ArrayList<>();

    for (String key_name : new ArrayList<>(data.keySet()))
      return_value.add(data.get(key_name));


    return return_value;
  }

  private Core plugin;
  public Repository(Core plugin_) {
    plugin = plugin_;
  }

}