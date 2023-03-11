package mccc.core.local;

import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Fallback {

  final private String config_path = "./config/core.yaml";

  public List<Team> fetch_from_config() {
    try {
      YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File(config_path));

      List<Team> return_value = new ArrayList<>();

      for (Object team_raw : configuration.getList("teams")) {
        LinkedHashMap<String, Object> team = (LinkedHashMap<String, Object>) team_raw;

        List<Player> team_players = new ArrayList<>();

        for (Object player_raw : (List<Object>) team.get("players")) {
          LinkedHashMap<String, Object> player = (LinkedHashMap<String, Object>) player_raw;

          team_players.add(new Player((String)player.get("nickname"), (Integer)player.get("score")));
        }

        return_value.add(new Team((String)team.get("name"), (Integer)team.get("score"), team_players));
      }


      System.out.println(return_value);

      return return_value;

    }

    catch (Exception e) {
      System.out.println("Fallback config fetch failed.");
      e.printStackTrace();
      return null;
    }
  }

}
