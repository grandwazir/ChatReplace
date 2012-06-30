/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ChatReplace.java is part of ChatReplace.
 * 
 * ChatReplace is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 * 
 * ChatReplace is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with ChatReplace.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.chatreplace;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import name.richardson.james.bukkit.chatreplace.append.AppendChatFormatter;
import name.richardson.james.bukkit.chatreplace.append.AppendPatternConfiguration;
import name.richardson.james.bukkit.chatreplace.management.ReloadCommand;
import name.richardson.james.bukkit.chatreplace.management.StatusCommand;
import name.richardson.james.bukkit.chatreplace.substitution.SubstitutionChatFormatter;
import name.richardson.james.bukkit.chatreplace.substitution.SubstitutionPatternConfiguration;
import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.command.CommandManager;


public class ChatReplace extends Plugin {

  private PluginManager pluginManager;
  private PluginDescriptionFile description;
  private ChatReplaceConfiguration configuration;
  private PlayerListener playerListener;
  private Set<ChatFormatter> formatters;
  private CommandManager commandManager;
  
  @Override
  public void onDisable() {
    logger.info(description.getName() + " is now disabled.");
  }

  @Override
  public void onEnable() {
    logger.setPrefix("[ChatReplace] ");
    pluginManager = this.getServer().getPluginManager();
    description = this.getDescription();
    
    try {
      loadConfiguration();
      loadFormatters();
      registerEvents();
      registerCommands();
    } catch (IOException exception) {
      logger.severe("Unable to load a configuration file!");
      this.pluginManager.disablePlugin(this);
    } finally {
      if (!this.pluginManager.isPluginEnabled(this)) {
        return;
      }
    }

    logger.info(description.getFullName() + " is now enabled.");
  }

  private void registerCommands() {
    this.setPermission();
    this.commandManager = new CommandManager(this.getDescription());
    this.getCommand("cr").setExecutor(this.commandManager);
    commandManager.registerCommand("reload", new ReloadCommand(this));
    commandManager.registerCommand("status", new StatusCommand(this));
  }

  private void loadFormatters() throws IOException {
    Set<ChatFormatter> formatters = new HashSet<ChatFormatter>();
    if (configuration.isSubstituting()) {
      final SubstitutionPatternConfiguration configuration = new SubstitutionPatternConfiguration(this, "substituition.yml");
      formatters.add(new SubstitutionChatFormatter(configuration));
    }
    if (configuration.isAppending()) {
      final AppendPatternConfiguration configuration = new AppendPatternConfiguration(this, "append.yml");
      formatters.add(new AppendChatFormatter(configuration));
    }
    this.formatters = formatters;
  }

  public int getTotalPatterns() {
    int patterns = 0;
    for (ChatFormatter formatter : formatters) {
      patterns = patterns + formatter.getPatternCount();
    }
    return patterns;
  }
  
  public void reload() throws IOException {
    this.loadConfiguration();
    this.loadFormatters();
  }
  
  private void registerEvents() {
    playerListener = new PlayerListener(formatters);
    pluginManager.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Event.Priority.Low, this);
  }

  private void loadConfiguration() throws IOException {
    this.configuration = new ChatReplaceConfiguration(this);
    if (this.configuration.getDebugging()) Logger.enableDebugging("chatreplace");
  }
  
}
