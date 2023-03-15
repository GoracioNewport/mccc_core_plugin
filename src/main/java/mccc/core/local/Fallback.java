package mccc.core.local;

import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Fallback {

  final private String config_path = "./config/core.yml";

  public ArrayList<Team> fetch_from_config() {
    try {
      YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File(config_path));

      ArrayList<Team> return_value = new ArrayList<>();

      for (Object team_raw : configuration.getList("teams")) {
        LinkedHashMap<String, Object> team = (LinkedHashMap<String, Object>) team_raw;

        ArrayList<Player> team_players = new ArrayList<>();

        for (Object player_raw : (List<Object>) team.get("players")) {
          LinkedHashMap<String, Object> player = (LinkedHashMap<String, Object>) player_raw;

          team_players.add(new Player((String)player.get("nickname"), (Integer)player.get("score")));
        }

        return_value.add(new Team((String)team.get("name"), (String)team.get("color"), (Integer)team.get("score"), team_players));
      }

      return return_value;

    }

    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean write_to_config(List<Team> data) {
    try {
      YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File(config_path));
      configuration.set("teams", data);

      configuration.save(new File(config_path));
      
      // A dirty workaround, but this works so...

      String content = Files.readString(Paths.get(config_path));
      content = content.replace(" !!mccc.core.local.data.Team", "");
      Files.writeString(Paths.get(config_path), content, StandardCharsets.UTF_8);

      return true;
    }

    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

}
