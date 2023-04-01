package mccc.core.api;

import mccc.core.Core;
import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.ArrayList;


public class PlayerManager {

  public void setGlobalGamemode(GameMode gamemode) {

    for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
      if (!player.hasPermission("admin"))
        player.setGameMode(gamemode);

  }

  public ArrayList<Player> getPlayers() {

    ArrayList<Player> players = new ArrayList<>();

    for (Team team : plugin.apiManager.teamManager.getTeams().values())
      players.addAll(team.players);

    return players;

  }

  private final Core plugin;
  public PlayerManager(Core plugin_) {
    plugin = plugin_;
  }

}
