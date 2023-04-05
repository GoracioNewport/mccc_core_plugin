package mcup.core.commands;

import mcup.core.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AdminCommands implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if (!sender.hasPermission("admin")) {
      sender.sendMessage("Never gonna give you up, never gonna let you down");
      return false;
    }

    if (args.length == 0) {
      // TODO
      return false;
    }

    if (args[0].equals("score")) {
      if (args.length == 1) {
        // TODO
        return false;
      }

      if (args[1].equals("add")) {
        if (args.length == 2) {

          return false;
        }

        if (args[2].equals("player") || args[2].equals("team")) {
          if (args.length < 5) {

            return false;
          }

          String entityName = args[3];
          int amount = Integer.parseInt(args[4]);
          String message = null;

          if (args.length == 6)
            message = args[5];

          if (args[2].equals("player"))
            plugin.apiManager.scoreManager.addScorePlayer(entityName, amount, message);
          else
            plugin.apiManager.scoreManager.addScoreTeam(entityName, amount, message);
        }
      }
    }

    if (args[0].equals("permissions")) {
      if (args.length == 1) {
        // TODO
        return true;
      }

      if (args[1].equals("clear"))
        plugin.permissionManager.clearGroups();

      if (args[1].equals("init"))
        plugin.permissionManager.initializeTeamGroups();

      if (args[1].equals("fill"))
        plugin.permissionManager.fillTeamGroups();

      if (args[1].equals("give")) {
        if (args.length == 2) {
          // TODO
          return true;
        }

        plugin.permissionManager.assignPlayerToTeam(args[2]);
      }
    }

    if (args[0].equals("database")) {
      if (args.length == 1) {
        // TODO
        return false;
      }

      if (args[1].equals("fetch")) {
        plugin.repository.fetch();
      }

      if (args[1].equals("write")) {
        plugin.repository.write();
      }
    }

    if (Objects.equals(args[0], "sequence")) {

      if (args.length == 1) {
        // ...
      }

      if (Objects.equals(args[1], "start"))
        plugin.stageManager.startSequence();

      if (Objects.equals(args[1], "terminate"))
        plugin.stageManager.terminateSequence();

      if (Objects.equals(args[1], "restart"))
        plugin.stageManager.restartSequence();

      if (Objects.equals(args[1], "next"))
        plugin.stageManager.switchToNextStage();

      if (Objects.equals(args[1], "prev"))
        plugin.stageManager.switchToPreviousStage();

      if (Objects.equals(args[1], "current"))
        sender.sendMessage(plugin.stageManager.getCurrentStage().toString());
    }

    return true;
  }

  private final Core plugin;
  public AdminCommands(Core plugin_) {
    plugin = plugin_;
  }
}
