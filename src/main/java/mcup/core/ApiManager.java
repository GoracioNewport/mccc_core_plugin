package mcup.core;

import mcup.core.api.PlayerManager;
import mcup.core.api.ScoreManager;
import mcup.core.api.TeamManager;

public class ApiManager {

  private final Core plugin;
  public PlayerManager playerManager;
  public TeamManager teamManager;
  public ScoreManager scoreManager;

  public ApiManager(Core plugin_) {
    plugin = plugin_;

    playerManager = new PlayerManager(plugin);
    teamManager = new TeamManager(plugin);
    scoreManager = new ScoreManager(plugin);
  }



}
