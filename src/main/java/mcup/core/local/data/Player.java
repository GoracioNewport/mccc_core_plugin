package mcup.core.local.data;

public class Player {
  public String nickname;
  public Integer score;

  public Player(String nickname_, Integer score_) {
    nickname = nickname_;
    score = score_;
  }

  public Player() {
    nickname = "Fake Player";
    score = 0;
  }
}


