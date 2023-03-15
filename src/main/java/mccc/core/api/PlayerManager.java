package mccc.core.api;

import mccc.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerManager {

  public void setGlobalGamemode(GameMode gamemode) {

    for (Player player : Bukkit.getOnlinePlayers())
      if (!player.hasPermission("op"))
        player.setGameMode(gamemode);

  }


  private Core plugin;
  public PlayerManager(Core plugin_) {
    plugin = plugin_;
  }

}
