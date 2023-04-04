package mccc.core.listeners;

import mccc.core.Core;
import mccc.core.local.data.Team;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {


  @EventHandler
  public void on_player_join(PlayerJoinEvent event) {
    Team playerTeam = plugin.apiManager.teamManager.getTeamByPlayer(event.getPlayer().getName());

    if (playerTeam == null && !event.getPlayer().hasPermission("admin")) {
      plugin.getLogger().warning("Security notification: " + event.getPlayer().getName() +
        " tried joining the server from IP " + event.getPlayer().getAddress() + ", but they're not in the player list");
      event.getPlayer().kickPlayer("Player is not in the team list");
      return;
    }

    if (playerTeam != null)
      plugin.permissionManager.assignPlayerToTeam(event.getPlayer().getName());


    if (plugin.offlinePlayerScheduler.scheduledGamemode.containsKey(event.getPlayer().getName())) {
      event.getPlayer().setGameMode(plugin.offlinePlayerScheduler.scheduledGamemode.get(event.getPlayer().getName()));
      plugin.offlinePlayerScheduler.scheduledGamemode.remove(event.getPlayer().getName());
    }

    if (plugin.offlinePlayerScheduler.scheduledLocation.containsKey(event.getPlayer().getName())) {
      event.getPlayer().teleport(plugin.offlinePlayerScheduler.scheduledLocation.get(event.getPlayer().getName()));
      plugin.offlinePlayerScheduler.scheduledLocation.remove(event.getPlayer().getName());
    }

  }

  private final Core plugin;
  public PlayerListener(Core plugin_) {
    plugin = plugin_;
  }

}
