package mcup.core.api;

import mcup.core.Core;
import mcup.core.local.data.Player;
import mcup.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

import java.util.HashMap;
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

    int currentDelta = teamDeltaScore.getOrDefault(teamName, 0);
    teamDeltaScore.put(teamName, currentDelta + amount);

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
    addScorePlayerOnly(playerName, multiplyScore(amount));
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

    int currentDelta = playerDeltaScore.getOrDefault(playerName, 0);
    playerDeltaScore.put(playerName, currentDelta + score);

    plugin.apiManager.teamManager.setTeam(team.name, team);
  }

  public int getTeamDeltaScore(String teamName) {
    return teamDeltaScore.getOrDefault(teamName, 0);
  }

  public int getPlayerDeltaScore(String playerName) {
    return playerDeltaScore.getOrDefault(playerName, 0);
  }

  private HashMap<String, Integer> playerDeltaScore = new HashMap<>();
  private HashMap<String, Integer> teamDeltaScore = new HashMap<>();

  private final Core plugin;

  public ScoreManager(Core plugin_) {
    plugin = plugin_;
  }

}