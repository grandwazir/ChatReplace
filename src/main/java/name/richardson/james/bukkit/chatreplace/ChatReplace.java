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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import name.richardson.james.bukkit.chatreplace.append.AppendChatFormatter;
import name.richardson.james.bukkit.chatreplace.append.AppendPatternConfiguration;
import name.richardson.james.bukkit.chatreplace.management.ReloadCommand;
import name.richardson.james.bukkit.chatreplace.substitution.SubstitutionChatFormatter;
import name.richardson.james.bukkit.chatreplace.substitution.SubstitutionPatternConfiguration;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin;

public class ChatReplace extends SkeletonPlugin {

  private final Set<ChatFormatter> formatters = new LinkedHashSet<ChatFormatter>();
  
  private ChatReplaceConfiguration configuration;

  protected void registerCommands() {
    CommandManager commandManager = new CommandManager(this);
    this.getCommand("cr").setExecutor(commandManager);
    commandManager.addCommand(new ReloadCommand(this));
  }

  private void loadFormatters() throws IOException {
    this.formatters.clear();
    if (configuration.isSubstituting()) {
      final SubstitutionPatternConfiguration configuration = new SubstitutionPatternConfiguration(this, "substituition.yml");
      formatters.add(new SubstitutionChatFormatter(configuration));
    }
    if (configuration.isAppending()) {
      final AppendPatternConfiguration configuration = new AppendPatternConfiguration(this, "append.yml");
      formatters.add(new AppendChatFormatter(configuration));
    }
    logger.debug(this.getFormattedPatternCount());
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
  
  protected void registerEvents() {
    PlayerChatListener listener = new PlayerChatListener(Collections.unmodifiableSet(this.formatters));
    this.getServer().getPluginManager().registerEvents(listener, this);
  }
  
  public String getFormattedPatternCount() {
    Object[] arguments = {this.getTotalPatterns()};
    double[] limits = {0, 1, 2};
    String[] formats = {this.getMessage("no-patterns"), this.getMessage("one-pattern"), this.getMessage("many-patterns")};
    return this.getChoiceFormattedMessage("patterns-loaded", arguments, formats, limits);
  }

  protected void loadConfiguration() throws IOException {
    this.configuration = new ChatReplaceConfiguration(this);
    this.loadFormatters();
  }

  
  public String getGroupID() {
    return "name.richardson.james.bukkit";
  }

  public String getArtifactID() {
    return "chat-replace";
  }
  
}
