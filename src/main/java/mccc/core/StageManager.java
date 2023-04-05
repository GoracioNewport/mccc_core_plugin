package mccc.core;

import mccc.core.stages.GamemodeStage;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class StageManager {

  public ArrayList<GamemodeStage> stages = new ArrayList<>();
  public int currentStageIndex = 0;

  public void startSequence() {
    currentStageIndex = 0;
    fillSequence();

    if (stages.size() == 0)
      return;

    stages.get(currentStageIndex).load();
  }

  public void switchToNextStage() {
    stages.get(currentStageIndex).unload();

    currentStageIndex++;
    while (currentStageIndex < stages.size() && stages.get(currentStageIndex).skipCondition())
      currentStageIndex++;

    if (currentStageIndex < stages.size())
      stages.get(currentStageIndex).load();
  }

  public void switchToPreviousStage() {
    stages.get(currentStageIndex).unload();

    currentStageIndex--;
    while (currentStageIndex >= 0 && stages.get(currentStageIndex).skipCondition())
      currentStageIndex--;

    if (currentStageIndex >= 0)
      stages.get(currentStageIndex).load();
  }

  public void terminateSequence() {
    if (currentStageIndex < 0 || currentStageIndex >= stages.size())
      return;

    stages.get(currentStageIndex).unload();
  }

  public void restartSequence() {
    terminateSequence();
    startSequence();
  }

  public void fillSequence() {
    stages.clear();

    for (Class <? extends  GamemodeStage> stage : sequence) {
      try {
        Constructor<? extends GamemodeStage> constructor = stage.getConstructor(Core.class, JavaPlugin.class);
        stages.add(constructor.newInstance(core, plugin));
      }

      catch (Exception e) {
        e.printStackTrace();
        plugin.getLogger().warning("Unable to load stage " + stage.toString() + " to manager sequence!");
      }

    }
  }

  public GamemodeStage getCurrentStage() {
    if (currentStageIndex >= stages.size())
      return null;

    return stages.get(currentStageIndex);
  }


  private final JavaPlugin plugin;
  private final Core core;
  private final ArrayList<Class <? extends GamemodeStage>> sequence;
  public StageManager(Core core_, JavaPlugin plugin_, ArrayList<Class <? extends GamemodeStage>> sequence_) {
    plugin = plugin_;
    core = core_;
    sequence = sequence_;

    fillSequence();
  }
}
