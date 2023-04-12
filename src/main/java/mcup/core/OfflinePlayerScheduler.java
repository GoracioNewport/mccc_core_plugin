package mcup.core;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class OfflinePlayerScheduler {

  public HashMap<String, Location> scheduledLocation = new HashMap<>();
  public HashMap<String, GameMode> scheduledGamemode = new HashMap<>();
  public HashSet<String> scheduledInventory = new HashSet<>();
  public HashMap <String, ArrayList<ItemStack>> scheduledItems = new HashMap<>();
  public HashMap <String, ArrayList<PotionEffect>> scheduledEffects = new HashMap<>();

  public void remove(String playerName) {
    scheduledLocation.remove(playerName);
    scheduledGamemode.remove(playerName);
    scheduledInventory.remove(playerName);
    scheduledItems.remove(playerName);
    scheduledEffects.remove(playerName);
  }

  public void clear() {
    scheduledLocation.clear();
    scheduledGamemode.clear();
    scheduledInventory.clear();
    scheduledItems.clear();
    scheduledEffects.clear();
  }

  public void put(String playerName, Location location, GameMode gameMode, ArrayList<ItemStack> itemStackList,
                  ArrayList<PotionEffect> potionEffectList) {
    scheduledLocation.put(playerName, location);
    scheduledGamemode.put(playerName, gameMode);
    scheduledInventory.add(playerName);
    scheduledItems.put(playerName, itemStackList);
    scheduledEffects.put(playerName, potionEffectList);
  }
  private JavaPlugin plugin;
  public OfflinePlayerScheduler(JavaPlugin plugin_) {
    plugin = plugin_;
  }

}
