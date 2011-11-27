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
package name.richardson.james.chatreplace;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import name.richardson.james.chatreplace.append.AppendChatFormatter;
import name.richardson.james.chatreplace.substitution.SubstitutionChatFormatter;
import name.richardson.james.chatreplace.util.Logger;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class ChatReplace extends JavaPlugin {

  private PluginManager pluginManager;
  private PluginDescriptionFile description;
  private YamlConfiguration configuration;
  private PlayerListener playerListener;
  private Set<ChatFormatter> formatters;
  
  @Override
  public void onDisable() {
    Logger.info(description.getName() + " is now disabled.");
  }

  @Override
  public void onEnable() {
    pluginManager = this.getServer().getPluginManager();
    description = this.getDescription();
    
    try {
      configuration = loadConfiguration();
      formatters = loadFormatters();
      registerEvents();
    } catch (IOException exception) {
      Logger.severe("Unable to load a configuration file!");
      this.pluginManager.disablePlugin(this);
    } finally {
      if (!this.pluginManager.isPluginEnabled(this)) {
        return;
      }
    }

    Logger.info(description.getFullName() + " is now enabled.");
  }

  private Set<ChatFormatter> loadFormatters() throws IOException {
    Set<ChatFormatter> formatters = new HashSet<ChatFormatter>();
    if (configuration.getBoolean("formatters.substituition")) {
      File configurationFile = getConfigurationFile("substituition.yml");
      formatters.add(new SubstitutionChatFormatter(configurationFile));
    }
    if (configuration.getBoolean("formatters.append")) {
      File configurationFile = getConfigurationFile("append.yml");
      formatters.add(new AppendChatFormatter(configurationFile));
    }
    return formatters;
  }

  private void registerEvents() {
    playerListener = new PlayerListener(formatters);
    pluginManager.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Event.Priority.Low, this);
  }

  protected YamlConfiguration loadConfiguration() throws IOException {
    Logger.info("Loading configuration: config.yml.");
    File configurationFile = getConfigurationFile("config.yml");
    YamlConfiguration configuration = YamlConfiguration.loadConfiguration(configurationFile);
    configuration.addDefault("formatters.substituition", true);
    configuration.addDefault("formatters.append", true);
    configuration.options().copyDefaults(true);
    configuration.save(configurationFile);
    return configuration;
  }
  
  protected File getConfigurationFile(String name) {
    return new File(this.getDataFolder() + File.separator + name);
  }
  
}
