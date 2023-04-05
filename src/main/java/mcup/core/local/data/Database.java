package mcup.core.local.data;

import java.util.LinkedHashMap;

public class Database {
  public LinkedHashMap<String, Team> teams;
  public double multiplier;

  public Database(LinkedHashMap<String, Team> teams_, double multiplier_) {
    teams = teams_;
    multiplier = multiplier_;
  }
}
