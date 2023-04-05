package mcup.core.api;

import mcup.core.Core;
import mcup.core.local.data.Player;
import mcup.core.local.data.Team;

import java.util.*;

public class TeamManager {

  public ArrayList<String> getTeamNames() {
    return new ArrayList<>(getTeams().keySet());
  }

  public LinkedHashMap<String, Team> getTeams() {
    return plugin.repository.data.teams;
  }

  public Team getTeam(String teamName) {
    return getTeams().getOrDefault(teamName, null);
  }

  public Team getTeamByIndex(int index) {
    requestStructureUpdate();
    if (index < sortedTeamList.size())
      return sortedTeamList.get(index);
    else {
      plugin.getLogger().warning("Unable to get team by index " + index + ", index out of range");
      return null;
    }
  }

  public Team getTeamByPlayer(String playerName) {
    return playerToTeam.getOrDefault(playerName, null);
  }
  public ArrayList<Player> getTeamPlayers(String teamName) {
    Team team = getTeam(teamName);

    if (team == null) {
      plugin.getLogger().warning("Unable to get team players for team " + teamName + ", no such team");
      return null;
    }

    return team.players;
  }

  public Player getPlayerByTeam(String teamName, String playerName) {
    Team team = getTeam(teamName);

    if (team == null) {
      plugin.getLogger().warning("Unable to get " + playerName + " player instance for team " + teamName + ", no such team");
      return null;
    }

    for (Player player : team.players)
      if (Objects.equals(player.nickname, playerName))
        return player;

    plugin.getLogger().warning("Unable to get " + playerName + " player instance for team " + teamName + ", no such player");
    return null;
  }
  public void removeTeam(String teamName) {
    if (!plugin.repository.data.teams.containsKey(teamName)) {
      plugin.getLogger().warning("Unable to remove team " + teamName + ", no such team");
      return;
    }

    plugin.repository.data.teams.remove(teamName);
    requestStructureUpdate();
  }
  public void setTeam(String teamName, Team team) {
    plugin.repository.data.teams.put(teamName, team);
    requestStructureUpdate();
  }

  public void changeTeamName(String teamCurrentName, String teamChangeName) {
    Team team = getTeam(teamChangeName);

    if (team == null) {
      plugin.getLogger().warning("Unable to change name for team " + teamCurrentName + ", no such team");
      return;
    }

    removeTeam(teamCurrentName);
    team.name = teamChangeName;
    setTeam(teamChangeName, team);
  }

  public void changeTeamColor(String teamName, String teamColor) {
    Team team = getTeam(teamName);

    if (team == null) {
      plugin.getLogger().warning("Unable to change color for team " + teamName + ", no such team");
      return;
    }

    team.color = teamColor;
    setTeam(teamName, team);
  }

  public void assignColors() {

    ArrayList<String> colors;

    try {
      colors = (ArrayList<String>) plugin.getConfig().getList("teamColors");
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

    ArrayList<String> teamNames = getTeamNames();
    teamNames = new ArrayList<>(teamNames.subList(0, teamNames.size()));

    Collections.shuffle(colors, new Random(teamNames.hashCode()));

    LinkedHashMap<String, Team> teams = getTeams();

    int index = 0;
    for (Team team : teams.values()) {
      if (index >= colors.size())
        return;

      changeTeamColor(team.name, colors.get(index));
      index++;
    }
  }

  public ArrayList<Team> sortedTeamList;
  public void requestTeamSorting() {
    sortedTeamList = new ArrayList<>(getTeams().values());
    sortedTeamList.sort(Comparator.comparing(t -> t.score));
    Collections.reverse(sortedTeamList);
  }

  public HashMap<String, Team> playerToTeam;
  public void requestPlayerToTeamMapping() {
    playerToTeam = new HashMap<>();

    for (Team team : getTeams().values())
      for (Player player : team.players)
        playerToTeam.put(player.nickname, team);
  }

  public void requestStructureUpdate() {
    requestTeamSorting();
    requestPlayerToTeamMapping();
  }

  public void fetchRepository() {
    plugin.repository.fetch();
  }

  public void writeRepository() {
    plugin.repository.write();
  }

  private final Core plugin;
  public TeamManager(Core plugin_) {
    plugin = plugin_;
    requestStructureUpdate();
  }


}
