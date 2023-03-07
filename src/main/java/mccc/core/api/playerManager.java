package mccc.core.api;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class playerManager {

  public void setGlobalGamemode(GameMode gamemode) {

    for (Player player : Bukkit.getOnlinePlayers())
      if (!player.hasPermission("op"))
        player.setGameMode(gamemode);

  }

}
