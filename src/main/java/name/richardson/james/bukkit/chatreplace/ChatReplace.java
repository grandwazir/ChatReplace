/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * ChatReplace.java is part of ChatReplace.
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
package name.richardson.james.bukkit.chatreplace;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import name.richardson.james.bukkit.chatreplace.append.AppendChatFormatter;
import name.richardson.james.bukkit.chatreplace.append.AppendPatternConfiguration;
import name.richardson.james.bukkit.chatreplace.substitution.SubstitutionChatFormatter;
import name.richardson.james.bukkit.chatreplace.substitution.SubstitutionPatternConfiguration;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.plugin.AbstractPlugin;
import name.richardson.james.bukkit.chatreplace.management.ReloadCommand;

public class ChatReplace extends AbstractPlugin {

  /** The configuration. */
  private ChatReplaceConfiguration configuration;

  /** The chat formatters. */
  private final List<ChatFormatter> formatters = new CopyOnWriteArrayList<ChatFormatter>();

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.updater.Updatable#getArtifactID()
   */
  public String getArtifactID() {
    return "chat-replace";
  }

  /**
   * Gets the total patterns.
   * 
   * @return the total patterns
   */
  public int getTotalPatterns() {
    int patterns = 0;
    for (final ChatFormatter formatter : this.formatters) {
      patterns = patterns + formatter.getPatternCount();
    }
    return patterns;
  }

  /**
   * Reload.
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void reload() throws IOException {
    this.loadConfiguration();
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#loadConfiguration
   * ()
   */
  @Override
  protected void loadConfiguration() throws IOException {
    super.loadConfiguration();
    this.getCustomLogger().setDebugging(true);
    this.configuration = new ChatReplaceConfiguration(this);
    this.loadFormatters();
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#registerCommands
   * ()
   */
  @Override
  protected void registerCommands() {
    final CommandManager commandManager = new CommandManager(this);
    this.getCommand("cr").setExecutor(commandManager);
    commandManager.addCommand(new ReloadCommand(this));
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#registerEvents
   * ()
   */
  @Override
  protected void registerListeners() {
    final PlayerChatListener listener = new PlayerChatListener(this.formatters);
    this.getServer().getPluginManager().registerEvents(listener, this);
  }

  /**
   * Load formatters.
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void loadFormatters() throws IOException {
    this.formatters.clear();
    if (this.configuration.isSubstituting()) {
      final SubstitutionPatternConfiguration configuration = new SubstitutionPatternConfiguration(this);
      this.formatters.add(new SubstitutionChatFormatter(configuration, this.getPermissionManager()));
    }
    if (this.configuration.isAppending()) {
      final AppendPatternConfiguration configuration = new AppendPatternConfiguration(this);
      this.formatters.add(new AppendChatFormatter(configuration, this.getPermissionManager()));
    }
    this.getCustomLogger().info(this, "patterns-loaded", this.getTotalPatterns());
  }

}
