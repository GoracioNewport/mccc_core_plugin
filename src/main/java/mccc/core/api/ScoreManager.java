package mccc.core.api;

import mccc.core.Core;
import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Objects;

public class ScoreManager {

  public double get_multiplier() {
    return plugin.repository.data.multiplier;
  }
  public void set_multiplier(double multiplier) {
    plugin.repository.data.multiplier = multiplier;
  }

  public void set_score(String team_name, int score) {
    Team team = plugin.apiManager.teamManager.get_team(team_name);

    if (team == null) {
      plugin.getLogger().warning("Unable to set sore to team " + team_name + ", no such team");
      return;
    }

    team.score = score;
    plugin.apiManager.teamManager.set_team(team.name, team);
  }

  public void add_score(String team_name, int amount, String message, String creditor_name) {
    Team team = plugin.apiManager.teamManager.get_team(team_name);

    if (team == null) {
      plugin.getLogger().warning("Unable to add score to team " + team_name + ", no such team");
      return;
    }

    Player creditor = plugin.apiManager.teamManager.get_player_by_team(team.name, creditor_name);

    amount = (int)(amount * get_multiplier());
    set_score(team.name, team.score + amount);

    if (message != null) {
      String output = team.color + "[" + ChatColor.RESET + "+" + amount + team.color + "] " + ChatColor.RESET + message;

      for (Player player : team.players) {
        org.bukkit.entity.Player bukkit_player = Bukkit.getPlayer(player.nickname);
        if (bukkit_player != null)
          bukkit_player.sendMessage(output);
      }
    }

    if (creditor != null)
      add_player_score(creditor.nickname, amount);
  }

  public void add_score(String player_name, int amount, String message) {
    Team team = plugin.apiManager.teamManager.get_team_by_player(player_name);

    if (team == null) {
      plugin.getLogger().warning("Unable to add score to team with player " + player_name + ", no such team");
      return;
    }

    add_score(team.name, amount, message, player_name);
  }
  private void set_player_score(String player_name, int score) {
    Team team = plugin.apiManager.teamManager.get_team_by_player(player_name);

    if (team == null) {
      plugin.getLogger().warning("Unable to set player score for player " + player_name + ", no such team");
      return;
    }

    for (Player team_player : team.players)
      if (Objects.equals(team_player.nickname, player_name))
        team_player.score = score;

    plugin.apiManager.teamManager.set_team(team.name, team);
  }

  private void add_player_score(String player_name, int score) {
    Team team = plugin.apiManager.teamManager.get_team_by_player(player_name);

    if (team == null) {
      plugin.getLogger().warning("Unable to add player score for player " + player_name + ", no such team");
      return;
    }

    for (Player team_player : team.players)
      if (Objects.equals(team_player.nickname, player_name))
        team_player.score += score;

    plugin.apiManager.teamManager.set_team(team.name, team);
  }

  private final Core plugin;

  public ScoreManager(Core plugin_) {
    plugin = plugin_;
  }

}