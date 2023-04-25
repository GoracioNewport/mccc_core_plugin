package mcup.core.stages;

import mcup.core.Core;
import org.bukkit.plugin.java.JavaPlugin;

public class Idle extends GamemodeStage {
  public Idle(Core core_, JavaPlugin plugin_) {
    super(core_, plugin_);
    timeLimit = 15 * 20;
  }
}
