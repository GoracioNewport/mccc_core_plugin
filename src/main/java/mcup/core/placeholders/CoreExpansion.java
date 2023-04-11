package mcup.core.placeholders;

import mcup.core.Core;
import mcup.core.local.data.Player;
import mcup.core.local.data.Team;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Objects;

public class CoreExpansion extends PlaceholderExpansion {

  @Override
  public @NotNull String getAuthor() {
    return "GoracioNewport";
  }

  @Override
  public @NotNull String getIdentifier() {
    return "core";
  }

  @Override
  public @NotNull String getVersion() {
    return "1.0.0";
  }

  @Override
  public String onRequest(OfflinePlayer player, String params) {

    String[] parsedParams = params.split("_");

    if (Objects.equals(parsedParams[0], "player")) {
      if (Objects.equals(parsedParams[1], "team")) {
        LinkedHashMap<String, Team> teams = plugin.apiManager.teamManager.getTeams();

        for (Team team : teams.values()) {
          for (Player team_player : team.players) {
            if (team_player.nickname.equals(player.getName()))
              return team.color + team.name;
          }
        }

        return "No Team";
      }

    }

    if (Objects.equals(parsedParams[0], "team")) {

      int teamIndex;

      try {
        teamIndex = Integer.parseInt(parsedParams[1]);
      }

      catch (Exception e) {
        e.printStackTrace();
        return "INVALID TEAM INDEX";
      }

      Team team = plugin.apiManager.teamManager.getTeamByIndex(teamIndex);

      if (parsedParams.length < 3)
        return null;

      if (Objects.equals(parsedParams[2], "name"))
        return team.color + team.name + ChatColor.RESET;

      if (Objects.equals(parsedParams[2], "score"))
        return team.color + team.score.toString() + ChatColor.RESET;

      if (Objects.equals(parsedParams[2], "color"))
        return team.color;

      if (Objects.equals(parsedParams[2], "player")) {
        int playerIndex;

        try {
          playerIndex = Integer.parseInt(parsedParams[3]);
        }

        catch (Exception e) {
          e.printStackTrace();
          return "INVALID PLAYER INDEX";
        }

        ArrayList<Player> teamPlayers = team.players;
        Player teamPlayer;

        if (playerIndex < teamPlayers.size())
          teamPlayer = teamPlayers.get(playerIndex);
        else
          return "PLAYER INDEX OUT OF RANGE";

        if (parsedParams.length < 5)
          return null;

        if (Objects.equals(parsedParams[4], "name")) {
          return team.color + teamPlayer.nickname + ChatColor.RESET;
        }

        if (Objects.equals(parsedParams[4], "score")) {
          return team.color + teamPlayer.score.toString() + ChatColor.RESET;
        }
      }
    }

    if (Objects.equals(parsedParams[0], "scoreboard")) {
      int scoreboardWidth;

      try {
        scoreboardWidth = plugin.getConfig().getInt("scoreboardWidth");
      }
      catch (Exception e) {
        e.printStackTrace();
        return "SCOREBOARD WIDTH ERROR";
      }

      if (Objects.equals(parsedParams[1], "team")) {
        int teamIndex;
        try {
          teamIndex = Integer.parseInt(parsedParams[2]);
        }
        catch (Exception e) {
          e.printStackTrace();
          return "INVALID TEAM INDEX";
        }

        Team team = plugin.apiManager.teamManager.getTeamByIndex(teamIndex);

        if (Objects.equals(parsedParams[3], "header")) {
          ArrayList<String> teamHeader = new ArrayList<>(Arrays.asList("[№" + (teamIndex + 1) + "] " + team.name, "Score: " + team.score));
          return PlaceholderAPI.setPlaceholders(player, padStrings(teamHeader, scoreboardWidth));
        }

        if (Objects.equals(parsedParams[3], "players")) {
          ArrayList<String> teamPlayerNames = new ArrayList<>();
          for (Player team_player : team.players)
            teamPlayerNames.add(team_player.nickname);

          return PlaceholderAPI.setPlaceholders(player, padStrings(teamPlayerNames, scoreboardWidth));
        }
      }
    }

    return null;
  }


  private final Core plugin;

  public CoreExpansion(Core plugin_) {
    plugin = plugin_;
  }

  private String padStrings(ArrayList<String> strings, int length) {
    int contentsLength = 0;
    for (String string : strings)
      contentsLength += string.length();

    int spacesLeft = length - contentsLength;
    int stringsLeft = strings.size() - 1;

    StringBuilder total_string = new StringBuilder();

    for (int i = 0; i < strings.size() - 1; i++) {
      total_string.append(strings.get(i));
      int spaces_to_insert = spacesLeft / stringsLeft;
      stringsLeft--;
      total_string.append(" ".repeat(spaces_to_insert));
      spacesLeft -= spaces_to_insert;
    }

    total_string.append(strings.get(strings.size() - 1));

    return total_string.toString();
  }
}