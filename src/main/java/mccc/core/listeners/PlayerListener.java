package mccc.core.listeners;

import mccc.core.Core;
import mccc.core.local.data.Team;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {


  @EventHandler
  public void on_player_join(PlayerJoinEvent event) {
    Team player_team = plugin.apiManager.teamManager.get_team_by_player(event.getPlayer().getName());

    if (player_team == null) {
      plugin.getLogger().warning("Security notification: " + event.getPlayer().getName() +
        " tried joining the server from IP " + event.getPlayer().getAddress() + ", but they're not in the player list");
      event.getPlayer().kickPlayer("Player is not in the team list");
      return;
    }

    plugin.permissionManager.assign_player_to_team(event.getPlayer().getName());
  }

  private final Core plugin;
  public PlayerListener(Core plugin_) {
    plugin = plugin_;
  }

}
