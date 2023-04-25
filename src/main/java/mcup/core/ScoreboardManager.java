package mcup.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;

public class ScoreboardManager {

  public org.bukkit.scoreboard.ScoreboardManager scoreboardManager;

  public Scoreboard tabBoard;

  public void initTeams() {

    ArrayList <Team> legacyTeams = new ArrayList<>();

    for (mcup.core.local.data.Team team : plugin.apiManager.teamManager.getTeams().values()) {
      Team legacyTeam = tabBoard.getTeam(team.name);

      if (legacyTeam != null)
        legacyTeam.unregister();

      legacyTeam = tabBoard.registerNewTeam(team.name);

      legacyTeam.setColor(ChatColor.getByChar(team.color.charAt(1)));
      legacyTeam.setDisplayName(team.color + team.name);
      legacyTeam.setAllowFriendlyFire(false);
    }



  }

  protected Core plugin;
  public ScoreboardManager(Core plugin_) {
    plugin = plugin_;
    scoreboardManager = Bukkit.getScoreboardManager();
    tabBoard = scoreboardManager.getMainScoreboard();
  }

}
