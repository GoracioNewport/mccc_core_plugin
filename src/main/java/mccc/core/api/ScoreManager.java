package mccc.core.api;

import mccc.core.Core;
import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ScoreManager {

  public double get_multiplier() {
    return plugin.repository.data.multiplier;
  }
  public void set_multiplier(double multiplier) {
    plugin.repository.data.multiplier = multiplier;
  }

  public void set_score(Team team, int score) {
    team.score = score;
    plugin.apiManager.teamManager.set_team(team.name, team);
  }

  public void add_score(Team team, int amount, String message, Player creditor) {
    amount = (int)(amount * get_multiplier());
    set_score(team, team.score + amount);

    if (message != null) {
      String output = team.color + "[" + ChatColor.RESET + "+" + amount + team.color + "] " + ChatColor.RESET + message;

      for (Player player : team.players) {
        org.bukkit.entity.Player bukkit_player = Bukkit.getPlayer(player.nickname);
        if (bukkit_player != null)
          bukkit_player.sendMessage(output);
      }
    }

    if (creditor != null)
      add_player_score(team, creditor, amount);
  }

  public void add_score(Player player, int amount, String message) {
    Team team = plugin.apiManager.teamManager.get_team_by_player(player.nickname);
    add_score(team, amount, message, player);
  }
  private void set_player_score(Team team, Player player, int score) {
    for (Player team_player : team.players)
      if (team_player == player)
        team_player.score = score;
    plugin.apiManager.teamManager.set_team(team.name, team);
  }

  private void add_player_score(Team team, Player player, int score) {
    for (Player team_player : team.players)
      if (team_player == player)
        team_player.score += score;
    plugin.apiManager.teamManager.set_team(team.name, team);
  }

  private final Core plugin;

  public ScoreManager(Core plugin_) {
    plugin = plugin_;
  }

}