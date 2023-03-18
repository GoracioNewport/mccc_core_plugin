package mccc.core;

import mccc.core.commands.Testing;
import mccc.core.local.Repository;
import mccc.core.placeholders.CoreExpansion;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

  public Repository repository;
  public ApiManager apiManager;

  public CoreExpansion placeholderManager;
  public LuckPerms permissionManager;

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

    // LuckPerms initialization
    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    if (provider != null)
      permissionManager = provider.getProvider();


    // API initialization
    apiManager = new ApiManager(this);
    apiManager.teamManager.assign_colors();

    // Placeholders initialization
    placeholderManager = new CoreExpansion(this);
    placeholderManager.register();

  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    repository.write();
  }
}
