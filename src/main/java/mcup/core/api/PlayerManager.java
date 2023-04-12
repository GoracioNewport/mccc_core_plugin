package mcup.core.api;

import mcup.core.Core;
import mcup.core.local.data.Player;
import mcup.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;


public class PlayerManager {

  public void setPlayerGamemode(GameMode gamemode, String playerName) {
    org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(playerName);

    if (bukkitPlayer == null)
      plugin.offlinePlayerScheduler.scheduledGamemode.put(playerName, gamemode);
    else
      bukkitPlayer.setGameMode(gamemode);
  }
  public void setGamemode(GameMode gamemode, ArrayList<String> target) {

    for (String playerName : target)
      setPlayerGamemode(gamemode, playerName);

  }

  public void setPlayersGamemode(GameMode gamemode) {

    ArrayList<String> playerNames = new ArrayList<>();

    for (Player player : getPlayers())
      playerNames.add(player.nickname);

    setGamemode(gamemode, playerNames);
  }

  public void setTeamGamemode(GameMode gamemode, Team team) {

    ArrayList<String> teamPlayerNames = new ArrayList<>();

    for (Player player : team.players)
      teamPlayerNames.add(player.nickname);

    setGamemode(gamemode, teamPlayerNames);
  }

  @Deprecated
  public void setGlobalGamemode(GameMode gamemode) {
    setPlayersGamemode(gamemode);
  }

  public void playerTeleport(Location location, String playerName) {

    org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(playerName);

    if (bukkitPlayer == null)
      plugin.offlinePlayerScheduler.scheduledLocation.put(playerName, location);
    else
      bukkitPlayer.teleport(location);
  }

  public void teamTeleport(ArrayList<Location> locations, Team team) {
    if (locations.size() != team.players.size())
      plugin.getLogger().warning("Tried to teleport team to a location, but player count and locations count does not match");

    for (int i = 0; i < team.players.size(); i++)
      playerTeleport(locations.get(i % locations.size()), team.players.get(i).nickname);
  }

  public void teamTeleport(Location location, Team team) {

    for (Player player : team.players)
      playerTeleport(location, player.nickname);

  }

  public void playSound(Sound sound, float pitch, Collection<? extends org.bukkit.entity.Player> target) {
    for (org.bukkit.entity.Player player : target)
      if (sound != null)
        player.playSound(player, sound, 1.0f, pitch);
  }

  public void playTeamSound(Sound sound, float pitch, Team team) {
    Collection<org.bukkit.entity.Player> target = new ArrayList<>();
    for (Player teamPlayer : team.players) {
      org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(teamPlayer.nickname);

      if (bukkitPlayer != null)
        target.add(bukkitPlayer);
    }

    playSound(sound, pitch, target);
  }

  public void sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut, Collection<? extends org.bukkit.entity.Player> target) {

    for (org.bukkit.entity.Player player : target)
      player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);

  }

  public void sendTeamTitle(String  title, String subTitle, int fadeIn, int stay, int fadeOut, Team team) {

    Collection<org.bukkit.entity.Player> target = new ArrayList<>();
    for (Player teamPlayer : team.players) {
      org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(teamPlayer.nickname);

      if (bukkitPlayer != null)
        target.add(bukkitPlayer);
    }

    sendTitle(title, subTitle, fadeIn, stay, fadeOut, target);

  }

  public void clearPlayerInventory(String playerName) {
    org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(playerName);

    if (bukkitPlayer == null)
      plugin.offlinePlayerScheduler.scheduledInventory.add(playerName);
    else
      bukkitPlayer.getInventory().clear();
  }

  public void clearInventory(ArrayList<String> target) {
    for (String player : target)
      clearPlayerInventory(player);
  }

  public void clearTeamInventory(Team team) {
    ArrayList<String> teamPlayerNames = new ArrayList<>();

    for (Player player : team.players)
      teamPlayerNames.add(player.nickname);

    clearInventory(teamPlayerNames);
  }

  public void clearPlayersInventory() {

    ArrayList<String> playerNames = new ArrayList<>();

    for (Player player : getPlayers())
      playerNames.add(player.nickname);

    clearInventory(playerNames);
  }

  public void givePlayerItems(ArrayList<ItemStack> itemStackList, String playerName) {
    org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(playerName);

    if (bukkitPlayer == null)
      plugin.offlinePlayerScheduler.scheduledItems.put(playerName, itemStackList);
    else
      bukkitPlayer.getInventory().setContents(itemStackList.toArray(new ItemStack[itemStackList.size()]));
  }

  public void giveItems(ArrayList<ItemStack> itemStackList, ArrayList<String> target) {
    for (String player : target)
      givePlayerItems(itemStackList, player);
  }

  public void giveTeamItems(ArrayList<ItemStack> itemStackList, Team team) {
    ArrayList<String> teamPlayerNames = new ArrayList<>();

    for (Player player : team.players)
      teamPlayerNames.add(player.nickname);

    giveItems(itemStackList, teamPlayerNames);
  }

  public void givePlayerEffects(ArrayList<PotionEffect> potionEffectList, String playerName) {
    org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(playerName);

    if (bukkitPlayer == null)
      plugin.offlinePlayerScheduler.scheduledEffects.put(playerName, potionEffectList);
    else
      bukkitPlayer.addPotionEffects(potionEffectList);
  }

  public void giveEffects(ArrayList<PotionEffect> potionEffectList, ArrayList<String> target) {
    for (String player : target)
      givePlayerEffects(potionEffectList, player);
  }

  public void giveTeamEffects(ArrayList<PotionEffect> potionEffectList, Team team) {
    ArrayList<String> teamPlayerNames = new ArrayList<>();

    for (Player player : team.players)
      teamPlayerNames.add(player.nickname);

    giveEffects(potionEffectList, teamPlayerNames);
  }

  public void givePlayersEffects(ArrayList<PotionEffect> potionEffectList) {

    ArrayList<String> playerNames = new ArrayList<>();

    for (Player player : getPlayers())
      playerNames.add(player.nickname);

    giveEffects(potionEffectList, playerNames);
  }

  public ArrayList<org.bukkit.entity.Player> getOnlinePlayers() {
    ArrayList<org.bukkit.entity.Player> target = new ArrayList<>();

    for (Player player : getPlayers()) {
      org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(player.nickname);

      if (bukkitPlayer != null)
        target.add(bukkitPlayer);
    }

    return target;
  }

  public ArrayList<org.bukkit.entity.Player> getOnlineTeamPlayers(Team team) {

    ArrayList<org.bukkit.entity.Player> target = new ArrayList<>();

    for (Player player : team.players) {
      org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(player.nickname);

      if (bukkitPlayer != null)
        target.add(bukkitPlayer);
    }

    return target;
  }

  public ArrayList<Player> getPlayers() {

    ArrayList<Player> players = new ArrayList<>();

    for (Team team : plugin.apiManager.teamManager.getTeams().values())
      players.addAll(team.players);

    return players;

  }

  private final Core plugin;
  public PlayerManager(Core plugin_) {
    plugin = plugin_;
  }

}
