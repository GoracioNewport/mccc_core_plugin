package mccc.core.placeholders;

import mccc.core.Core;
import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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

    if (params.equalsIgnoreCase("player_team")) {
      LinkedHashMap<String, Team> teams = plugin.apiManager.teamManager.get_teams();

      for (Team team : teams.values()) {
        for (Player team_player : team.players) {
          if (team_player.nickname.equals(player.getName()))
            return team.color + team.name;
        }
      }

      return "No Team";
    }

    if (params.startsWith("team")) {

      String[] parsed_params = params.split("_");
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
        return team.color + team.name;

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
          return team.color + team_player.nickname;
        }

        if (Objects.equals(parsed_params[4], "score")) {
          return team_player.score.toString();
        }
      }
    }


    return null;
  }


  private final Core plugin;

  public CoreExpansion(Core plugin_) {
    plugin = plugin_;
  }
}