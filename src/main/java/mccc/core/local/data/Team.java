package mccc.core.local.data;

import java.util.List;

public class Team {
  public String name;
  public Integer score;

  public List<Player> players;

  public Team(String name_, Integer score_, List<Player> players_) {
    name = name_;
    score = score_;
    players = players_;
  }
}
