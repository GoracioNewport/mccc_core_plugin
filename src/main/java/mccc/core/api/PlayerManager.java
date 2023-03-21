package mccc.core.api;

import mccc.core.Core;
import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.Objects;

public class PlayerManager {

  public void setGlobalGamemode(GameMode gamemode) {

    for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
      if (!player.hasPermission("admin"))
        player.setGameMode(gamemode);

  }

  public Player get_player_object(String player_name) {
    Team player_team = plugin.apiManager.teamManager.get_team_by_player(player_name);

    if (player_team == null)
      return null;

    for (Player player : player_team.players)
      if (Objects.equals(player.nickname, player_name))
        return player;

    return null;
  }


  private Core plugin;
  public PlayerManager(Core plugin_) {
    plugin = plugin_;
  }

}
