package mccc.core;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class OfflinePlayerScheduler {

  public HashMap<String, Location> scheduledLocation;
  public HashMap<String, GameMode> scheduledGamemode;

  public void remove(String playerName) {
    scheduledLocation.remove(playerName);
    scheduledGamemode.remove(playerName);
  }

  public void clear() {
    scheduledLocation.clear();
    scheduledGamemode.clear();
  }

  public void put(String playerName, Location location, GameMode gameMode) {
    scheduledLocation.put(playerName, location);
    scheduledGamemode.put(playerName, gameMode);
  }
  private JavaPlugin plugin;
  public OfflinePlayerScheduler(JavaPlugin plugin_) {
    plugin = plugin_;
  }

}
