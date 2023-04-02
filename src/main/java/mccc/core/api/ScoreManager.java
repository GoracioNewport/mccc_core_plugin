package mccc.core.api;

import mccc.core.Core;
import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.util.Objects;

public class ScoreManager {

  public double getMultiplier() {
    return plugin.repository.data.multiplier;
  }
  public void setMultiplier(double multiplier) {
    plugin.repository.data.multiplier = multiplier;
  }

  public void addMultiplier(double addition) {
    setMultiplier(getMultiplier() + addition);
  }

  private int multiplyScore(int amount) {
    return (int)(amount * getMultiplier());
  }

  public void setScoreTeam(String teamName, int score) {
    Team team = plugin.apiManager.teamManager.getTeam(teamName);

    if (team == null) {
      plugin.getLogger().warning("Unable to set sore to team " + teamName + ", no such team");
      return;
    }

    team.score = score;
    plugin.apiManager.teamManager.setTeam(team.name, team);
  }
  

  public void addScoreTeam(String teamName, int amount, String message) {
    Team team = plugin.apiManager.teamManager.getTeam(teamName);

    if (team == null) {
      plugin.getLogger().warning("Unable to add score to team " + teamName + ", no such team");
      return;
    }

    amount = multiplyScore(amount);
    setScoreTeam(team.name, team.score + amount);

    if (message != null) {
      String output = team.color + "[" + ChatColor.RESET + "+" + amount + team.color + "] " + ChatColor.RESET + message;

      for (Player player : team.players) {
        org.bukkit.entity.Player bukkit_player = Bukkit.getPlayer(player.nickname);
        if (bukkit_player != null) {
          bukkit_player.sendMessage(output);
          bukkit_player.playSound(bukkit_player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
        }
      }
    }
  }

  public void addScorePlayer(String playerName, int amount, String message) {
    Team team = plugin.apiManager.teamManager.getTeamByPlayer(playerName);

    if (team == null) {
      plugin.getLogger().warning("Unable to add score to team with player " + playerName + ", no such team");
      return;
    }

    addScoreTeam(team.name, amount, message);
    addScorePlayerOnly(playerName, (int)(amount * getMultiplier()));
  }
  private void setScorePlayerOnly(String playerName, int score) {
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

  private void addScorePlayerOnly(String playerName, int score) {
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