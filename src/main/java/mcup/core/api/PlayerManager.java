package mcup.core.api;

import mcup.core.Core;
import mcup.core.local.data.Player;
import mcup.core.local.data.Team;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;

import java.sql.Time;
import java.util.ArrayList;


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

  public void playGlobalSound(Sound sound, float pitch) {
    for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
      player.playSound(player, sound, 1.0f, pitch);
    }
  }

  public void sendGlobalTitle(String title, String subTitle, Title.Times times, Sound sound, float pitch) {

    Audience audience = plugin.adventureApi.players();

    audience.showTitle(Title.title(
      Component.text(title),
      Component.text(subTitle),
      times
    ));

    if (sound != null)
      playGlobalSound(sound, pitch);
  }

  public void sendGlobalTitle(String title, String subTitle) {
    sendGlobalTitle(title, subTitle, Title.DEFAULT_TIMES, null, 1.0f);
  }

  public void sendGlobalTitle(String title, String subtitle, Sound sound, float pitch) {
    sendGlobalTitle(title, subtitle, Title.DEFAULT_TIMES, sound, pitch);
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
