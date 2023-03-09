package mccc.core.local;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Fallback {

  public void pull_from_config() {
    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(new File("config.yaml"));

    System.out.println(configuration.getString("kek"));
  }

}
