package mccc.core;

import mccc.core.local.Repository;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;

public final class Core extends JavaPlugin {

  Repository repository;

  @Override
  public void onEnable() {
    // Plugin startup logic
    System.out.println("MCCC Core plugin started");
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
