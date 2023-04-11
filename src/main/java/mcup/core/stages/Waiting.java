package mcup.core.stages;

import mcup.core.Core;
import mcup.core.local.data.Player;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.plugin.java.JavaPlugin;

public class Waiting extends GamemodeStage {


  @Override
  public String getDisplayName() {
    return "Ожидание";
  }

  @Override
  public void load() {
    super.load();

    initBossBarCountdown();
    core.apiManager.playerManager.setGlobalGamemode(GameMode.ADVENTURE);
  }

  @Override
  protected String getBossBarCountdownLabel() {
    return "Ожидание игроков" + " .".repeat(Math.max(0, getSecondsElapsed() % 3 + 1));
  }

  @Override
  public boolean endCondition() {
    for (Player player : core.apiManager.playerManager.getPlayers()) {
      org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(player.nickname);

      if (bukkitPlayer == null || !bukkitPlayer.isOnline())
        return false;
    }

    return true;
  }

  public Waiting(Core core_, JavaPlugin plugin_) {
    super(core_, plugin_);
  }

}
