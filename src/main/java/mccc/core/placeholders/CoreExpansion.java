package mccc.core.placeholders;

import mccc.core.Core;
import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.checkerframework.checker.units.qual.A;
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

    String[] parsed_params = params.split("_");

    if (Objects.equals(parsed_params[0], "player")) {
      if (Objects.equals(parsed_params[1], "team")) {
        LinkedHashMap<String, Team> teams = plugin.apiManager.teamManager.get_teams();

        for (Team team : teams.values()) {
          for (Player team_player : team.players) {
            if (team_player.nickname.equals(player.getName()))
              return team.color + team.name;
          }
        }

        return "No Team";
      }

    }

    if (Objects.equals(parsed_params[0], "team")) {

      int team_index;

      try {
        team_index = Integer.parseInt(parsed_params[1]);
      }

      catch (Exception e) {
        e.printStackTrace();
        return "INVALID TEAM INDEX";
      }

      Team team = plugin.apiManager.teamManager.get_team_by_index(team_index);

      if (parsed_params.length < 3)
        return null;

      if (Objects.equals(parsed_params[2], "name"))
        return team.color + team.name + ChatColor.RESET;

      if (Objects.equals(parsed_params[2], "score"))
        return team.color + team.score.toString() + ChatColor.RESET;

      if (Objects.equals(parsed_params[2], "color"))
        return team.color;

      if (Objects.equals(parsed_params[2], "player")) {
        int player_index;

        try {
          player_index = Integer.parseInt(parsed_params[3]);
        }

        catch (Exception e) {
          e.printStackTrace();
          return "INVALID PLAYER INDEX";
        }

        ArrayList<Player> team_players = team.players;
        Player team_player;

        if (player_index < team_players.size())
          team_player = team_players.get(player_index);
        else
          return "PLAYER INDEX OUT OF RANGE";

        if (parsed_params.length < 5)
          return null;

        if (Objects.equals(parsed_params[4], "name")) {
          return team.color + team_player.nickname + ChatColor.RESET;
        }

        if (Objects.equals(parsed_params[4], "score")) {
          return team.color + team_player.score.toString() + ChatColor.RESET;
        }
      }
    }

    if (Objects.equals(parsed_params[0], "scoreboard")) {
      int scoreboard_width;

      try {
        scoreboard_width = plugin.getConfig().getInt("scoreboard_width");
      }
      catch (Exception e) {
        e.printStackTrace();
        return "SCOREBOARD WIDTH ERROR";
      }

      if (Objects.equals(parsed_params[1], "team")) {
        int team_index;
        try {
          team_index = Integer.parseInt(parsed_params[2]);
        }
        catch (Exception e) {
          e.printStackTrace();
          return "INVALID TEAM INDEX";
        }

        Team team = plugin.apiManager.teamManager.get_team_by_index(team_index);

        if (Objects.equals(parsed_params[3], "header")) {
          ArrayList<String> team_header = new ArrayList<>(Arrays.asList("[№" + (team_index + 1) + "] " + team.name, "Score: " + team.score));
          return PlaceholderAPI.setPlaceholders(player, pad_strings(team_header, scoreboard_width));
        }

        if (Objects.equals(parsed_params[3], "players")) {
          ArrayList<String> team_player_names = new ArrayList<>();
          for (Player team_player : team.players)
            team_player_names.add(team_player.nickname);

          return PlaceholderAPI.setPlaceholders(player, pad_strings(team_player_names, scoreboard_width));
        }
      }
    }

    return null;
  }


  private final Core plugin;

  public CoreExpansion(Core plugin_) {
    plugin = plugin_;
  }

  private String pad_strings(ArrayList<String> strings, int length) {
    int contents_length = 0;
    for (String string : strings)
      contents_length += string.length();

    int spaces_left = length - contents_length;
    int strings_left = strings.size() - 1;

    StringBuilder total_string = new StringBuilder();

    for (int i = 0; i < strings.size() - 1; i++) {
      total_string.append(strings.get(i));
      int spaces_to_insert = spaces_left / strings_left;
      strings_left--;
      total_string.append(" ".repeat(spaces_to_insert));
      spaces_left -= spaces_to_insert;
    }

    total_string.append(strings.get(strings.size() - 1));

    return total_string.toString();
  }
}