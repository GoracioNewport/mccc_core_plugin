package mcup.core.api;

import mcup.core.Core;
import mcup.core.local.data.Player;
import mcup.core.local.data.Team;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.Collection;


public class PlayerManager {

  public void setGlobalGamemode(GameMode gamemode) {

    ArrayList<Player> players = getPlayers();

    for (Player player : players) {
      org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(player.nickname);

      if (bukkitPlayer == null)
        plugin.offlinePlayerScheduler.scheduledGamemode.put(player.nickname, gamemode);
      else
        bukkitPlayer.setGameMode(gamemode);
    }

  }

  public void globalTeleport(ArrayList<Location> locations) {

    ArrayList<Player> players = getPlayers();

    for (int i = 0; i < locations.size(); i++) {
      org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(players.get(i).nickname);

      if (bukkitPlayer == null)
        plugin.offlinePlayerScheduler.scheduledLocation.put(players.get(i).nickname, locations.get(i));
      else
        bukkitPlayer.teleport(locations.get(i));
    }

  }

  public void teamTeleport(ArrayList<Location> locations, Team team) {

    for (int i = 0; i < locations.size(); i++) {
      if (i >= team.players.size())
        break;

      org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(team.players.get(i).nickname);

      if (bukkitPlayer == null)
        plugin.offlinePlayerScheduler.scheduledLocation.put(team.players.get(i).nickname, locations.get(i));
      else
        bukkitPlayer.teleport(locations.get(i));
    }

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

  public void clearInventory(Collection<? extends org.bukkit.entity.Player> target) {
    for (org.bukkit.entity.Player player : target)
      player.getInventory().clear();
  }

  public void clearTeamInventory(Team team) {
    clearInventory(getOnlineTeamPlayers(team));
  }

  public void clearPlayersInventory() {
    clearInventory(getOnlinePlayers());
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
