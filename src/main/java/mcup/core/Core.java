package mcup.core;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import mcup.core.commands.AdminCommands;
import mcup.core.listeners.GamemodeListener;
import mcup.core.listeners.PacketListener;
import mcup.core.listeners.PlayerListener;
import mcup.core.local.Repository;
import mcup.core.placeholders.CoreExpansion;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Core extends JavaPlugin {

  public Repository repository;
  public ApiManager apiManager;
  public StageManager stageManager;

  public CoreExpansion placeholderManager;
  public PermissionManager permissionManager;

  public ScoreboardManager scoreboardManager;

  public ProtocolManager protocolManager;
  public BukkitAudiences adventureApi;

  public OfflinePlayerScheduler offlinePlayerScheduler;

  @Override
  public void onEnable() {

    // Plugin startup logic
    getLogger().info("MCup Core plugin started");

    // Commands registration
    if (getCommand("core") == null)
      getLogger().warning("Unable to register commands");
    else
      Objects.requireNonNull(getCommand("core")).setExecutor(new AdminCommands(this));

    // Default core configuration
    saveDefaultConfig();

    // Repository initialization
    repository = new Repository(this);
    repository.fetch();

    // API initialization
    apiManager = new ApiManager(this);
    apiManager.teamManager.assignColors();
    adventureApi = BukkitAudiences.create(this);
    offlinePlayerScheduler = new OfflinePlayerScheduler(this);

    scoreboardManager = new ScoreboardManager(this);
    scoreboardManager.initTeams();

    // LuckPerms initialization
    permissionManager = new PermissionManager(this);


    // ProtocolLib initialization
    protocolManager = ProtocolLibrary.getProtocolManager();
    protocolManager.addPacketListener(new PacketListener(this, PacketType.Play.Server.ENTITY_METADATA));


    // Placeholders initialization
    placeholderManager = new CoreExpansion(this);
    placeholderManager.register();

    // Listeners initialization
    getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    getServer().getPluginManager().registerEvents(new GamemodeListener(this), this);

  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
    adventureApi.close();
  }

  public void registerStageManager(StageManager stageManager_) {
    stageManager = stageManager_;
  }
}
