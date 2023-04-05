package mcup.core.api;

import mcup.core.Core;
import mcup.core.local.data.Player;
import mcup.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

import java.util.ArrayList;


public class PlayerManager {

  public void setGlobalGamemode(GameMode gamemode) {

    ArrayList<Player> players = getPlayers();

    for (Player player : players) {
      org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(player.nickname);

      if (bukkitPlayer == null)
        plugin.offlinePlayerScheduler.scheduledGamemode.put(player.nickname, gamemode);
      else
        bukkitPlayer.setGameMode(gamemode);
    }

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
