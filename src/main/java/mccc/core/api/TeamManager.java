package mccc.core.api;

import mccc.core.Core;
import mccc.core.local.data.Player;
import mccc.core.local.data.Team;

import java.util.*;

public class TeamManager {

  public ArrayList<String> get_team_names() {
    return new ArrayList<>(get_teams().keySet());
  }

  public LinkedHashMap<String, Team> get_teams() {
    return plugin.repository.data.teams;
  }

  public Team get_team(String team_name) {
    return get_teams().getOrDefault(team_name, null);
  }

  public Team get_team_by_index(int index) {
    request_structure_update();
    if (index < sorted_team_list.size())
      return sorted_team_list.get(index);
    else {
      plugin.getLogger().warning("Unable to get team by index " + index + ", index out of range");
      return null;
    }
  }

  public Team get_team_by_player(String player_name) {
    return player_to_team.getOrDefault(player_name, null);
  }
  public ArrayList<Player> get_team_players(String team_name) {
    Team team = get_team(team_name);

    if (team == null) {
      plugin.getLogger().warning("Unable to get team players for team " + team_name + ", no such team");
      return null;
    }

    return team.players;
  }

  public Player get_player_by_team(String team_name, String player_name) {
    Team team = get_team(team_name);

    if (team == null) {
      plugin.getLogger().warning("Unable to get " + player_name + " player instance for team " + team_name + ", no such team");
      return null;
    }

    for (Player player : team.players)
      if (Objects.equals(player.nickname, player_name))
        return player;

    plugin.getLogger().warning("Unable to get " + player_name + " player instance for team " + team_name + ", no such player");
    return null;
  }
  public void remove_team(String team_name) {
    if (!plugin.repository.data.teams.containsKey(team_name)) {
      plugin.getLogger().warning("Unable to remove team " + team_name + ", no such team");
      return;
    }

    plugin.repository.data.teams.remove(team_name);
    request_structure_update();
  }
  public void set_team(String team_name, Team team) {
    plugin.repository.data.teams.put(team_name, team);
    request_structure_update();
  }

  public void change_team_name(String team_current_name, String team_change_name) {
    Team team = get_team(team_change_name);

    if (team == null) {
      plugin.getLogger().warning("Unable to change name for team " + team_current_name + ", no such team");
      return;
    }

    remove_team(team_current_name);
    team.name = team_change_name;
    set_team(team_change_name, team);
  }

  public void change_team_color(String team_name, String team_color) {
    Team team = get_team(team_name);

    if (team == null) {
      plugin.getLogger().warning("Unable to change color for team " + team_name + ", no such team");
      return;
    }

    team.color = team_color;
    set_team(team_name, team);
  }

  public void assign_colors() {

    ArrayList<String> colors;

    try {
      colors = (ArrayList<String>) plugin.getConfig().getList("team_colors");
    }

    catch (Exception e) {
      e.printStackTrace();
      plugin.getLogger().warning("Unable to read team colors from configuration");
      return;
    }

    if (colors == null) {
      plugin.getLogger().warning("Unable to load team colors list!");
      return;
    }

    ArrayList<String> team_names = get_team_names();
    team_names = new ArrayList<>(team_names.subList(0, team_names.size()));

    Collections.shuffle(colors, new Random(team_names.hashCode()));

    LinkedHashMap<String, Team> teams = get_teams();

    int index = 0;
    for (Team team : teams.values()) {
      if (index >= colors.size())
        return;

      change_team_color(team.name, colors.get(index));
      index++;
    }
  }

  public ArrayList<Team> sorted_team_list;
  public void request_team_sorting() {
    sorted_team_list = new ArrayList<>(get_teams().values());
    sorted_team_list.sort(Comparator.comparing(t -> t.score));
    Collections.reverse(sorted_team_list);
  }

  public HashMap<String, Team> player_to_team;
  public void request_player_to_team_mapping() {
    player_to_team = new HashMap<>();

    for (Team team : get_teams().values())
      for (Player player : team.players)
        player_to_team.put(player.nickname, team);
  }

  public void request_structure_update() {
    request_team_sorting();
    request_player_to_team_mapping();
  }

  private final Core plugin;
  public TeamManager(Core plugin_) {
    plugin = plugin_;
    request_structure_update();
  }


}
