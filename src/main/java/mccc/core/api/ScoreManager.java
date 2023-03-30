package mccc.core.api;

import mccc.core.Core;
import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Objects;

public class ScoreManager {

  public double getMultiplier() {
    return plugin.repository.data.multiplier;
  }
  public void setMultiplier(double multiplier) {
    plugin.repository.data.multiplier = multiplier;
  }

  public void setScore(String teamName, int score) {
    Team team = plugin.apiManager.teamManager.getTeam(teamName);

    if (team == null) {
      plugin.getLogger().warning("Unable to set sore to team " + teamName + ", no such team");
      return;
    }

    team.score = score;
    plugin.apiManager.teamManager.setTeam(team.name, team);
  }
  

  public void addScore(String teamName, int amount, String message, String creditorName) {
    Team team = plugin.apiManager.teamManager.getTeam(teamName);

    if (team == null) {
      plugin.getLogger().warning("Unable to add score to team " + teamName + ", no such team");
      return;
    }

    Player creditor = plugin.apiManager.teamManager.getPlayerByTeam(team.name, creditorName);

    amount = (int)(amount * getMultiplier());
    setScore(team.name, team.score + amount);

    if (message != null) {
      String output = team.color + "[" + ChatColor.RESET + "+" + amount + team.color + "] " + ChatColor.RESET + message;

      for (Player player : team.players) {
        org.bukkit.entity.Player bukkit_player = Bukkit.getPlayer(player.nickname);
        if (bukkit_player != null)
          bukkit_player.sendMessage(output);
      }
    }

    if (creditor != null)
      addPlayerScore(creditor.nickname, amount);
  }

  public void addScore(String playerName, int amount, String message) {
    Team team = plugin.apiManager.teamManager.getTeamByPlayer(playerName);

    if (team == null) {
      plugin.getLogger().warning("Unable to add score to team with player " + playerName + ", no such team");
      return;
    }

    addScore(team.name, amount, message, playerName);
  }
  private void setPlayerScore(String playerName, int score) {
    Team team = plugin.apiManager.teamManager.getTeamByPlayer(playerName);

    if (team == null) {
      plugin.getLogger().warning("Unable to set player score for player " + playerName + ", no such team");
      return;
    }

    for (Player team_player : team.players)
      if (Objects.equals(team_player.nickname, playerName))
        team_player.score = score;

    plugin.apiManager.teamManager.setTeam(team.name, team);
  }

  private void addPlayerScore(String playerName, int score) {
    Team team = plugin.apiManager.teamManager.getTeamByPlayer(playerName);

    if (team == null) {
      plugin.getLogger().warning("Unable to add player score for player " + playerName + ", no such team");
      return;
    }

    for (Player team_player : team.players)
      if (Objects.equals(team_player.nickname, playerName))
        team_player.score += score;

    plugin.apiManager.teamManager.setTeam(team.name, team);
  }

  private final Core plugin;

  public ScoreManager(Core plugin_) {
    plugin = plugin_;
  }

}