package mcup.core.local.data;

import java.util.ArrayList;

public class Team {
  public String name;

  public String color;
  public Integer score;

  public ArrayList<Player> players;

  public Team(String name_, String color_, Integer score_, ArrayList<Player> players_) {
    name = name_;
    color = color_;
    score = score_;
    players = players_;
  }

  public Team() {
    name = "Empty Team";
    color = "§4";
    score = 0;
    players = new ArrayList<>();
  }
}
