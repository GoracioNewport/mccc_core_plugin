package mccc.core.local;

import mccc.core.local.data.Team;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Repository {

  private LinkedHashMap<String, Team> teams;
  private Fallback fallback = new Fallback();

  private List<Team> fetch_from_database() {
    // TODO
    return null;
  }

  private boolean write_to_database(List<Team> data) {
    // TODO
    return false;
  }

  public void set_teams(LinkedHashMap<String, Team> data) {
    teams = data;
  }

  public LinkedHashMap<String, Team> get_teams() {
    return teams;
  }

  public void fetch() {

    List<Team> data;

    data = fetch_from_database();

    if (data != null) {
      set_teams(list_to_map(data));
      System.out.println("Configuration successfully fetched from database");
      return;
    }

    System.out.println("Database configuration fetch failed");

    data = fallback.fetch_from_config();

    if (data != null) {
      set_teams(list_to_map(data));
      System.out.println("Configuration fallback fetch system operational");
      return;
    }

    System.out.println("Fallback configuration fetch failed");
    System.out.println("CRITICAL: UNABLE TO LOAD CONFIGURATION");
  }

  public void write() {

    if (write_to_database(map_to_list(get_teams()))) {
      System.out.println("Configuration successfully written to database");
    }

    System.out.println("Database configuration write failed");


    if (fallback.write_to_config(map_to_list(get_teams()))) {
      System.out.println("Configuration fallback write system operational");
      return;
    }

    System.out.println("Fallback configuration write failed");
    System.out.println("CRITICAL: UNABLE TO SAVE CONFIGURATION");
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

}