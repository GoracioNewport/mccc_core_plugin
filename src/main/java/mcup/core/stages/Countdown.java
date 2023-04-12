package mcup.core.stages;

import mcup.core.Core;
import mcup.core.local.data.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Countdown extends GamemodeStage {
  @Override
  public void tick() {
    super.tick();
  }

  @Override
  public void tickSecond() {
    super.tickSecond();
    updateScreenCountdown(super.getSecondsLeft());
  }

  protected ChatColor getTitleColor(int secondsRemaining) {
    ChatColor titleColor;

    if (secondsRemaining > 5)
      titleColor = ChatColor.YELLOW;

    else if (secondsRemaining > 3)
      titleColor = ChatColor.GOLD;

    else if (secondsRemaining > 1)
      titleColor = ChatColor.RED;

    else
      titleColor = ChatColor.DARK_RED;

    return titleColor;
  }

  protected String getSubTitle(int secondsRemaining) {
    String subTitle = "";

    if (secondsRemaining % 5 == 0 && secondsRemaining >= 10) {
      subTitle = "секунд до начала";
    }

    return subTitle;
  }

  protected boolean updateSkipCondition(int secondsRemaining) {
    if (secondsRemaining > 10 && secondsRemaining % 5 != 0)
      return true;

    if (secondsRemaining == 0)
      return true;

    return false;
  }

  protected Title.Times getTitleTimes(int secondsRemaining) {
    if (secondsRemaining > 10)
      return Title.Times.times(Duration.ofMillis(500), Duration.ofMillis(1500), Duration.ofMillis(500));
    else
      return Title.Times.times(Duration.ZERO, Duration.ofSeconds(1000), Duration.ZERO);
  }

  public void updateScreenCountdown(int secondsRemaining) {
    final Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();

    if (updateSkipCondition(secondsRemaining))
      return;

    ChatColor titleColor = getTitleColor(secondsRemaining);

    String currentTitle = titleColor + "" + secondsRemaining;
    String subTitle = getSubTitle(secondsRemaining);

    playersAudience.showTitle(
      Title.title(
        Component.text(currentTitle),
        Component.text(subTitle),
        getTitleTimes(secondsRemaining)
    ));

    // How the hell do I play a note with AdventureAPI ?
    for (Player player : onlinePlayers) {
      // playing a note
      int tone = 10 - secondsRemaining;

      if (tone >= 0 && tone <= 24)
        player.playNote(player.getLocation(), Instrument.PIANO, new Note(tone));
      else
        player.playSound(player, Sound.UI_BUTTON_CLICK, 1, 1);
    }
  }

  @Override
  public void load() {
    super.load();
    core.apiManager.playerManager.setGlobalGamemode(GameMode.ADVENTURE);
  }

  @Override
  public String getDisplayName() {
    return "Обратный отсчёт";
  }

  protected int spawnOffset = 1;
  public void buildCage(Material fill) {
    for (Location location : spawnLocations) {
      Location anchor = location.clone().add(0, 1, 0);

      int radius = spawnOffset + 1;

      for (int i = -radius - 1; i <= radius + 1; i++) {
        anchor.clone().add(i, 0, -radius - 1).getBlock().setType(fill);
        anchor.clone().add(i, 0, radius + 1).getBlock().setType(fill);
        anchor.clone().add(-radius - 1, 0, i).getBlock().setType(fill);
        anchor.clone().add(radius + 1, 0, i).getBlock().setType(fill);
      }
    }
  }

  public void spawnPlayers() {

    int locationIndex = 0;
    for (Team team : core.apiManager.teamManager.getTeams().values()) {

      Location teamLocation = spawnLocations.get(locationIndex).clone();
      ArrayList< mcup.core.local.data.Player> shuffledPlayers = (ArrayList<mcup.core.local.data.Player>) team.players.clone();
      Collections.shuffle(shuffledPlayers);


      for (int i = 0; i < team.players.size(); i++) {
        int offsetX = (int)Math.pow(-1, i / 2) * spawnOffset;
        int offsetZ = (int)Math.pow(-1, i % 2) * spawnOffset;

        Location playerLocation = teamLocation.clone().add(offsetX, 0, offsetZ);

        mcup.core.local.data.Player teamPlayer = shuffledPlayers.get(i);
        core.apiManager.playerManager.playerTeleport(playerLocation, teamPlayer.nickname);
      }

      locationIndex++;
    }

  }

  public ArrayList <Location> spawnLocations = new ArrayList<>();

  protected void getSpawnLocations() {
    spawnLocations.clear();

    for (int i = 0; i < core.apiManager.teamManager.getTeams().size(); i++) {
      Location location = plugin.getConfig().getLocation("spawn." + i + ".location");
//      int offset = plugin.getConfig().getInt("spawn." + i + ".offset");
      spawnLocations.add(location);
    }

    Collections.shuffle(spawnLocations);
  }

  public Countdown(Core core_, JavaPlugin plugin_) {
    super(core_, plugin_);
    timeLimit = 20 * 20;
  }

}
