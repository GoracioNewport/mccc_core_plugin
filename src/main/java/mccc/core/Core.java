package mccc.core;

import mccc.core.local.Fallback;
import mccc.core.local.Repository;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

  Repository repository;

  @Override
  public void onEnable() {

    // Plugin startup logic
    System.out.println("MCCC Core plugin started");
    new Fallback().pull_from_config();
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
