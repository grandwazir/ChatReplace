/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ReloadCommand.java is part of TimedMessages.
 * 
 * TimedMessages is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * TimedMessages is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * TimedMessages. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.chatreplace.management;

import java.io.IOException;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import name.richardson.james.bukkit.chatreplace.ChatReplace;
import name.richardson.james.bukkit.utilities.command.CommandArgumentException;
import name.richardson.james.bukkit.utilities.command.CommandPermissionException;
import name.richardson.james.bukkit.utilities.command.CommandUsageException;
import name.richardson.james.bukkit.utilities.command.PluginCommand;

public class ReloadCommand extends PluginCommand {

  public static final String NAME = "reload";
  public static final String DESCRIPTION = "Reload patterns and configuration.";
  public static final String PERMISSION_DESCRIPTION = "Allow users to reload patterns and configuration.";
  public static final String USAGE = "";

  public static final Permission PERMISSION = new Permission("chatreplace.reload", PERMISSION_DESCRIPTION, PermissionDefault.OP);

  private final ChatReplace plugin;

  public ReloadCommand(ChatReplace plugin) {
    super(plugin);
    this.plugin = plugin;
    this.registerPermissions();
  }

  
  private void registerPermissions() {
    final String prefix = plugin.getDescription().getName().toLowerCase() + ".";
    // create the base permission
    Permission base = new Permission(prefix + this.getName(), plugin.getMessage("reloadcommand-permission-description"), PermissionDefault.OP);
    base.addParent(plugin.getRootPermission(), true);
    this.addPermission(base);
  }

  public void execute(CommandSender sender) throws CommandArgumentException, CommandPermissionException, name.richardson.james.bukkit.utilities.command.CommandUsageException {
    try {
      plugin.reload();
    } catch (IOException exception) {
      Bukkit.getPluginManager().disablePlugin(plugin);
      throw new CommandUsageException(this.getMessage("panic"));
    }
    sender.sendMessage(this.getSimpleFormattedMessage("reloadcommand-complete", plugin.getName()));
  }
  

  public void parseArguments(String[] arguments, CommandSender sender) throws CommandArgumentException {
    return;
  }

}
