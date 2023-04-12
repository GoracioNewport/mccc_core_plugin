package mcup.core.local;

import mcup.core.Core;
import mcup.core.local.data.Database;
import mcup.core.local.data.Player;
import mcup.core.local.data.Team;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Fallback {

  final private String configPath;
  final private String configName;

  final private Converter converter = new Converter();

  public Database fetchFromConfig() {
    try {
      YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File(configPath + configName));

      ArrayList<Team> teams = new ArrayList<>();

      for (Object teamRaw : configuration.getList("teams")) {
        LinkedHashMap<String, Object> team = (LinkedHashMap<String, Object>) teamRaw;

        ArrayList<Player> teamPlayers = new ArrayList<>();

        for (Object player_raw : (List<Object>) team.get("players")) {
          LinkedHashMap<String, Object> player = (LinkedHashMap<String, Object>) player_raw;

          teamPlayers.add(new Player((String)player.get("nickname"), (Integer)player.get("score")));
        }

        teams.add(new Team((String)team.get("name"), (String)team.get("color"), (Integer)team.get("score"), teamPlayers));
      }

      return new Database(converter.listToMap(teams), configuration.getDouble("multiplier"));
    }

    catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean write_to_config(Database data) {
    try {
      YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File(configPath + configName));
      configuration.save(new File(configPath + "backup/old_" + System.currentTimeMillis() + configName));

      configuration.set("teams", converter.mapToList(data.teams));
      configuration.set("multiplier", data.multiplier);

      configuration.save(new File(configPath + configName));
      
      // A dirty workaround, but this works so...

      Path path = Paths.get(configPath + configName);
      String content = Files.readString(path);
      content = content.replace(" !!mcup.core.local.data.Team", "");
      Files.writeString(path, content, StandardCharsets.UTF_8);

      return true;
    }

    catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  private Core plugin;
  public Fallback(Core plugin_) {
    plugin = plugin_;
    configPath = plugin.getConfig().getString("fallbackLocation");
    configName = plugin.getConfig().getString("fallbackName");
  }

}
