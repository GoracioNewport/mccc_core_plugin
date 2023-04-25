package mcup.core.listeners;

import mcup.core.Core;
import mcup.core.local.data.Team;
import mcup.core.stages.Cutscene;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerListener implements Listener {


  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Team playerTeam = plugin.apiManager.teamManager.getTeamByPlayer(event.getPlayer().getName());

    if (playerTeam == null && !event.getPlayer().hasPermission("admin")) {
      plugin.getLogger().warning("Security notification: " + event.getPlayer().getName() +
        " tried joining the server from IP " + event.getPlayer().getAddress() + ", but they're not in the player list");
      event.getPlayer().kickPlayer("Player is not in the team list");
      return;
    }

    if (playerTeam != null) {
      org.bukkit.scoreboard.Team legacyTeam = plugin.scoreboardManager.tabBoard.getTeam(playerTeam.name);
      if (legacyTeam != null)
        legacyTeam.addEntry(event.getPlayer().getName());
    }


    plugin.offlinePlayerScheduler.checkPlayerJoinSchedule(event.getPlayer());
  }

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {
    plugin.offlinePlayerScheduler.checkPlayerRespawnSchedule(event.getPlayer());
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent event) {
    if (plugin.stageManager.getCurrentStage() instanceof Cutscene) {
      event.setCancelled(true);
    }
  }

  private final Core plugin;
  public PlayerListener(Core plugin_) {
    plugin = plugin_;
  }

}
