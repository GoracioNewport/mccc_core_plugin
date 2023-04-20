package mcup.core;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;

public class OfflinePlayerScheduler {

  public HashMap<String, Location> scheduledLocation = new HashMap<>();
  public HashMap<String, GameMode> scheduledGamemode = new HashMap<>();
  public HashMap <String, ArrayList<ItemStack>> scheduledItems = new HashMap<>();
  public HashMap <String, ArrayList<PotionEffect>> scheduledEffects = new HashMap<>();

  public HashMap <String, Location> scheduledSpawnPoint = new HashMap<>();

  public void checkPlayerJoinSchedule(Player player) {

    String playerName = player.getName();

    if (scheduledLocation.containsKey(playerName)) {
      player.teleport(scheduledLocation.get(playerName));
      scheduledLocation.remove(playerName);
    }

    if (scheduledGamemode.containsKey(playerName)) {
      player.setGameMode(scheduledGamemode.get(playerName));
      scheduledGamemode.remove(playerName);
    }

    if (scheduledItems.containsKey(playerName)) {
      if (scheduledItems.get(playerName) == null)
        player.getInventory().clear();
      else
        for (ItemStack itemStack : scheduledItems.get(playerName))
          player.getInventory().addItem(itemStack);

      scheduledItems.remove(playerName);
    }

    if (scheduledEffects.containsKey(playerName)) {
      if (scheduledEffects.get(playerName) == null)
        for (PotionEffect potionEffect : player.getActivePotionEffects())
          player.removePotionEffect(potionEffect.getType());
      else
        player.addPotionEffects(scheduledEffects.get(playerName));

      scheduledEffects.remove(playerName);
    }

  }

  public void checkPlayerRespawnSchedule(Player player) {

    String playerName = player.getName();

    if (scheduledSpawnPoint.containsKey(playerName))
      player.teleport(scheduledSpawnPoint.get(playerName));

  }

  public void clear() {
    scheduledLocation.clear();
    scheduledGamemode.clear();
    scheduledItems.clear();
    scheduledEffects.clear();
    scheduledSpawnPoint.clear();
  }
  private final JavaPlugin plugin;
  public OfflinePlayerScheduler(JavaPlugin plugin_) {
    plugin = plugin_;
  }

}
