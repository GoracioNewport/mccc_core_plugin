package mccc.core.stages;

import mccc.core.Core;
import mccc.core.events.GamemodeStageEndEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;


public class GamemodeStage {

  public void load() {
    playersAudience = core.adventureApi.players();
    tickTimer = new BukkitRunnable() {
      public void run() {
        tick();
      }
    }.runTaskTimer(plugin, 1, 1);
  }

  public void unload() {
    if (bossBarEnabled)
      playersAudience.hideBossBar(bossBarInstance);


    if (tickTimer != null)
      tickTimer.cancel();
  }

  public void tick() {
    if (endCondition() || (timeLimit != -1 && (timeElapsed + 1) >= timeLimit)) {
      plugin.getServer().getPluginManager().callEvent(new GamemodeStageEndEvent(this));
    }

    if (timeElapsed % 20 == 0) {
      tickSecond();
    }

    timeElapsed++;
  }

  public void tickSecond() {
    if (bossBarEnabled) {
      if (timeLimit != -1)
        bossBarInstance.progress((float)getSecondsLeft() / (float)getSecondsTotal());
      bossBarInstance.name(Component.text(getBossBarCountdownLabelPrefix()));
      playersAudience.showBossBar(bossBarInstance);
    }
  }


  public int timeLimit = -1;

  public int timeElapsed = 0;

  public BukkitTask tickTimer;

  public int getSecondsLeft() {
    return (timeLimit - timeElapsed) / 20;
  }

  public String getSecondsLeftFancy() {
    int seconds = getSecondsLeft();
    int minutes = seconds / 60;
    seconds = seconds % 60;

    String fancyText = minutes + ":";
    if (seconds < 10)
      fancyText = fancyText + "0";

    fancyText = fancyText + seconds;

    return fancyText;
  }

  public int getSecondsElapsed() {
    return timeElapsed / 20;
  }

  public int getSecondsTotal() {
    return timeLimit / 20;
  }

  public boolean endCondition() {
    return false;
  }

  public boolean skipCondition() {
    return endCondition();
  }

  protected final JavaPlugin plugin;
  protected final Core core;

  protected boolean bossBarEnabled = false;
  protected Audience playersAudience;
  protected BossBar bossBarInstance;

  protected String bossBarCountdownLabelPrefix = "До конца фазы: ";
  protected String bossBarCountdownLabelSuffix = "";

  protected String getBossBarCountdownLabelPrefix() {
    return bossBarCountdownLabelPrefix + getSecondsLeft() + bossBarCountdownLabelSuffix;
  }

  protected void initBossBarCountdown() {

    if (endCondition())
      return;

    bossBarInstance = BossBar.bossBar(Component.text(getBossBarCountdownLabelPrefix()), 1, BossBar.Color.RED, BossBar.Overlay.NOTCHED_20);

    playersAudience.showBossBar(bossBarInstance);

    bossBarEnabled = true;
  }

  public String getDisplayName() {
    return "None";
  }


  public GamemodeStage(Core core_, JavaPlugin plugin_) {
    plugin = plugin_;
    core = core_;
  }
}
