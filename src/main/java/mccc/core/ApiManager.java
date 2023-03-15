package mccc.core;

import mccc.core.api.PlayerManager;
import mccc.core.api.ScoreManager;
import mccc.core.api.TeamManager;

public class ApiManager {

  private Core plugin;
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
