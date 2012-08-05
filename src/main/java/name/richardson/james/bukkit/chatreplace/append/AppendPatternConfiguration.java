/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AppendPatternConfiguration.java is part of ChatReplace.
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
package name.richardson.james.bukkit.chatreplace.append;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.chatreplace.ChatReplace;
import name.richardson.james.bukkit.chatreplace.PatternConfiguration;
import name.richardson.james.bukkit.utilities.persistence.AbstractYAMLStorage;

public class AppendPatternConfiguration extends AbstractYAMLStorage implements PatternConfiguration {

  public static final String FILE_NAME = "substitution.yml";
  
  /** The patterns created from this configuration file. */
  private final List<AppendPattern> patterns = new ArrayList<AppendPattern>();

  /**
   * Instantiates a new append pattern configuration.
   * 
   * @param plugin the plugin
   * @param fileName the file name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public AppendPatternConfiguration(final ChatReplace plugin) throws IOException {
    super(plugin, FILE_NAME);
    this.setDefaultPatterns();
    this.setPatterns();
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.chatreplace.PatternConfiguration#getPatterns()
   */
  public List<AppendPattern> getPatterns() {
    return Collections.unmodifiableList(this.patterns);
  }
  
  private void setDefaultPatterns() {
    if (this.getConfiguration().getKeys(false).isEmpty()) {
      this.getConfiguration().createSection("example-pattern");
      this.getConfiguration().getConfigurationSection("example-pattern").set("pattern", "[hello]");
      this.getConfiguration().getConfigurationSection("example-pattern").set("append-location", "end");
      this.getConfiguration().getConfigurationSection("example-pattern").set("replacements", Arrays.asList("bonjour", "gutentag"));
    }
    this.save();
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.chatreplace.PatternConfiguration#setPatterns()
   */
  public void setPatterns() {
    for (final String node : this.getConfiguration().getKeys(false)) {
      final String permission = this.getConfiguration().getString(node + ".permission");
      final String pattern = this.getConfiguration().getString(node + ".pattern");
      final String appendAt = this.getConfiguration().getString(node + ".append-location");
      final List<?> values = this.getConfiguration().getStringList(node + ".replacements");
      final AppendPattern newPattern = new AppendPattern(pattern, values, permission, AppendPattern.Location.valueOf(appendAt.toUpperCase()));
      this.patterns.add(newPattern);
    }
  }

}
