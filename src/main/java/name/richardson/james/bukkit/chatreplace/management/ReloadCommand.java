/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ReloadCommand.java is part of ChatReplace.
 * 
 * ChatReplace is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ChatReplace is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ChatReplace. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.chatreplace.management;

import java.io.IOException;

import org.bukkit.Bukkit;
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

  public ReloadCommand(final ChatReplace plugin) {
    super(plugin);
    this.plugin = plugin;
    this.registerPermissions();
  }

  public void execute(final CommandSender sender) throws CommandArgumentException, CommandPermissionException, name.richardson.james.bukkit.utilities.command.CommandUsageException {
    try {
      this.plugin.reload();
    } catch (final IOException exception) {
      Bukkit.getPluginManager().disablePlugin(this.plugin);
      throw new CommandUsageException(this.getMessage("panic"));
    }
    sender.sendMessage(this.getSimpleFormattedMessage("complete", this.plugin.getName()));
  }

  public void parseArguments(final String[] arguments, final CommandSender sender) throws CommandArgumentException {
    return;
  }

  private void registerPermissions() {
    final String prefix = this.plugin.getDescription().getName().toLowerCase() + ".";
    // create the base permission
    final Permission base = new Permission(prefix + this.getName(), this.getMessage("permission-description"), PermissionDefault.OP);
    base.addParent(this.plugin.getRootPermission(), true);
    this.addPermission(base);
  }

}
