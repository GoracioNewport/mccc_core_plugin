package mccc.core.api;

import mccc.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;


public class PlayerManager {

  public void setGlobalGamemode(GameMode gamemode) {

    for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers())
      if (!player.hasPermission("admin"))
        player.setGameMode(gamemode);

  }

  private final Core plugin;
  public PlayerManager(Core plugin_) {
    plugin = plugin_;
  }

}
