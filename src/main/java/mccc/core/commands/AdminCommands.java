package mccc.core.commands;

import mccc.core.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AdminCommands implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if (args.length == 0) {
      // TODO
      return true;
    }

    if (args[0].equals("permissions")) {
      if (args.length == 1) {
        // TODO
        return true;
      }

      if (args[1].equals("clear"))
        plugin.permissionManager.clear_groups();

      if (args[1].equals("init"))
        plugin.permissionManager.initialize_team_groups();

      if (args[1].equals("fill"))
        plugin.permissionManager.fill_team_groups();

      if (args[1].equals("give")) {
        if (args.length == 2) {
          // TODO
          return true;
        }

        plugin.permissionManager.assign_player_to_team(args[2]);
      }
    }

    if (args[0].equals("database")) {
      if (args.length == 1) {
        // TODO
        return true;
      }

      if (args[1].equals("fetch")) {
        plugin.repository.fetch();
      }

      if (args[1].equals("write")) {
        plugin.repository.write();
      }
    }

    return true;
  }

  private final Core plugin;
  public AdminCommands(Core plugin_) {
    plugin = plugin_;
  }
}
