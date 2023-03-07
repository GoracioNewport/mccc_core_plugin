package mccc.core;

import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

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
