package mccc.core;

import mccc.core.local.data.Player;
import mccc.core.local.data.Team;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class PermissionManager {

  public LuckPerms luckPerms;
  private final Core plugin;

  public void clearGroups() {
    luckPerms.getGroupManager().loadAllGroups();
    ArrayList <Group> groups = new ArrayList<>(luckPerms.getGroupManager().getLoadedGroups());

    ArrayList <String> immuneGroups = new ArrayList<>(plugin.getConfig().getStringList("serviceGroups"));

    for (Group group : groups) {
      if (immuneGroups.contains(group.getName()) || group.getName().equals("default"))
        continue;

      try {
        luckPerms.getGroupManager().deleteGroup(group);
      }

      catch (Exception e) {
        plugin.getLogger().warning(group.getName() + " was not found in storage during deletion");
      }
    }
  }

  public void initializeTeamGroups() {
    ArrayList <Team> teams = new ArrayList<>(plugin.apiManager.teamManager.getTeams().values());
    for (Team team : teams) {
      CompletableFuture<Group> receiver = luckPerms.getGroupManager().createAndLoadGroup(team.name);

      receiver.thenApplyAsync(group -> {
        PrefixNode prefixNode = PrefixNode.builder("[" + team.color + team.name + ChatColor.RESET + "] ", 101).build();
        group.data().add(prefixNode);
        luckPerms.getGroupManager().saveGroup(group);

        return group;
      });
    }

  }

  public void fillTeamGroups() {
    ArrayList <Team> teams = new ArrayList<>(plugin.apiManager.teamManager.getTeams().values());

    for (Team team : teams) {
      Group group = luckPerms.getGroupManager().getGroup(team.name);

      if (group == null) {
        plugin.getLogger().warning("Team " + team.name + " has no LuckPerms group, can't fill with players!");
        continue;
      }

      for (Player player : team.players) {

        InheritanceNode node = InheritanceNode.builder(group).value(true).build();

        org.bukkit.entity.Player bukkit_player = Bukkit.getPlayer(player.nickname);

        if (bukkit_player == null) {
          plugin.getLogger().warning("Player " + player.nickname + " was not found on server, can't fill group!");
          continue;
        }

        User user = luckPerms.getPlayerAdapter(org.bukkit.entity.Player.class).getUser(bukkit_player);
        user.data().add(node);

        luckPerms.getUserManager().saveUser(user);
      }
    }
  }

  public void assignPlayerToTeam(String player_name) {
    org.bukkit.entity.Player bukkit_player = Bukkit.getPlayer(player_name);
    Team team = plugin.apiManager.teamManager.getTeamByPlayer(player_name);

    if (bukkit_player == null) {
      plugin.getLogger().warning("Player " + player_name + " was not found, can't assign to team!");
      return;
    }

    if (team == null) {
      plugin.getLogger().warning("Player " + player_name + " doesn't belong to any team, can't assign!");
      return;
    }

    Group group = luckPerms.getGroupManager().getGroup(team.name);

    if (group == null) {
      plugin.getLogger().warning("Team " + team.name + " has no LuckPerms group, can't assign!");
      return;
    }

    InheritanceNode node = InheritanceNode.builder(group).value(true).build();

    User user = luckPerms.getPlayerAdapter(org.bukkit.entity.Player.class).getUser(bukkit_player);
    user.data().add(node);
    luckPerms.getUserManager().saveUser(user);
  }
  public PermissionManager(Core plugin_) {
    plugin = plugin_;

    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
    if (provider != null)
      luckPerms = provider.getProvider();
  }
}
