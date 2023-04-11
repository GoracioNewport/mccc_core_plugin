package mcup.core.events;

import mcup.core.stages.GamemodeStage;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class GamemodeStageEndEvent extends Event {
  private static final HandlerList handlers = new HandlerList();

  GamemodeStage stage;

  public GamemodeStage getStage() {
    return stage;
  }

  public GamemodeStageEndEvent(GamemodeStage stage_) {
    stage = stage_;
  }

  public @NotNull HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
