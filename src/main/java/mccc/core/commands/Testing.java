package mccc.core.commands;

import mccc.core.Core;
import mccc.core.local.data.Team;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Testing implements CommandExecutor {

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

    if (sender instanceof Player) {
      Player p = (Player) sender;

      for (Team team : plugin.apiManager.teamManager.get_teams().values()) {
        p.sendMessage(team.color + team.name);
      }
    }

    return true;
  }

  private Core plugin;
  public Testing(Core plugin_) {
    plugin = plugin_;
  }
}
