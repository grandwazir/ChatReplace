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
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import name.richardson.james.bukkit.chatreplace.append.AppendChatFormatter;
import name.richardson.james.bukkit.chatreplace.append.AppendPatternConfiguration;
import name.richardson.james.bukkit.chatreplace.management.ReloadCommand;
import name.richardson.james.bukkit.chatreplace.substitution.SubstitutionChatFormatter;
import name.richardson.james.bukkit.chatreplace.substitution.SubstitutionPatternConfiguration;
import name.richardson.james.bukkit.utilities.command.CommandManager;
import name.richardson.james.bukkit.utilities.formatters.ChoiceFormatter;
import name.richardson.james.bukkit.utilities.plugin.AbstractPlugin;

public class ChatReplace extends AbstractPlugin {

  /** The chat formatters. */
  private final List<ChatFormatter> formatters = new CopyOnWriteArrayList<ChatFormatter>();

  /** The ChoiceFormatter for number of patterns loaded */
  private ChoiceFormatter choiceFormatter;
  
  /** The configuration. */
  private ChatReplaceConfiguration configuration;

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.updater.Updatable#getArtifactID()
   */
  public String getArtifactID() {
    return "chat-replace";
  }

  /**
   * Gets the formatted pattern count.
   * 
   * @return the formatted pattern count
   */
  public String getFormattedPatternCount() {
    if (choiceFormatter == null) {
      this.choiceFormatter = new ChoiceFormatter(this.getLocalisation());
      this.choiceFormatter.setFormats(
        this.getLocalisation().getMessage(this, "no-patterns"),
        this.getLocalisation().getMessage(this, "one-pattern"),
        this.getLocalisation().getMessage(this, "many-patterns")
      );
      this.choiceFormatter.setLimits(0,1,2);
    }
    this.choiceFormatter.setArguments(this.getTotalPatterns());
    return this.choiceFormatter.getMessage();
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

  /**
   * Load formatters.
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void loadFormatters() throws IOException {
    this.formatters.clear();
    if (this.configuration.isSubstituting()) {
      final SubstitutionPatternConfiguration configuration = new SubstitutionPatternConfiguration(this, "substituition.yml");
      this.formatters.add(new SubstitutionChatFormatter(configuration));
    }
    if (this.configuration.isAppending()) {
      final AppendPatternConfiguration configuration = new AppendPatternConfiguration(this, "append.yml");
      this.formatters.add(new AppendChatFormatter(configuration));
    }
    this.getLogger().info(this.getFormattedPatternCount());
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
    // commandManager.addCommand(new ReloadCommand(this));
  }
  

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.plugin.SkeletonPlugin#registerEvents
   * ()
   */
  @Override
  protected void registerListeners() {
    final PlayerChatListener listener = new PlayerChatListener(Collections.unmodifiableSet(this.formatters));
    this.getServer().getPluginManager().registerEvents(listener, this);
  }

  protected void setupMetrics() throws IOException {
    if (this.configuration.isCollectingStats()) new MetricsListener(this);
  }
  
}
