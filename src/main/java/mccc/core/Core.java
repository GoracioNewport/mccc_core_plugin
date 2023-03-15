package mccc.core;

import mccc.core.commands.Testing;
import mccc.core.local.Repository;
import mccc.core.placeholders.CoreExpansion;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

  public Repository repository;
  public ApiManager apiManager;

  @Override
  public void onEnable() {

    // Plugin startup logic
    getLogger().info("MCCC Core plugin started");
    getCommand("test").setExecutor(new Testing(this));

    // Default core configuration
    saveDefaultConfig();

    // Repository initialization
    repository = new Repository(this);
    repository.fetch();

    // API initialization
    apiManager = new ApiManager(this);
    apiManager.teamManager.assign_colors();

    // Placeholders initialization
    new CoreExpansion(this).register();

  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    repository.write();
  }
}
