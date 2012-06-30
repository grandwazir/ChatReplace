package name.richardson.james.bukkit.chatreplace.append;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.chatreplace.ChatReplace;
import name.richardson.james.bukkit.chatreplace.PatternConfiguration;
import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;

public class AppendPatternConfiguration extends YAMLStorage implements PatternConfiguration {

  /** The patterns created from this configuration file. */
  private final Set<AppendPattern> patterns = new LinkedHashSet<AppendPattern>();

  /**
   * Instantiates a new append pattern configuration.
   * 
   * @param plugin the plugin
   * @param fileName the file name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public AppendPatternConfiguration(final ChatReplace plugin, final String fileName) throws IOException {
    super(plugin, fileName);
    this.setPatterns();
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.chatreplace.PatternConfiguration#getPatterns()
   */
  public Set<AppendPattern> getPatterns() {
    return Collections.unmodifiableSet(this.patterns);
  }

  /**
   * Sets the defaults values for this configuration file.
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void setDefaults() throws IOException {
    this.logger.debug(String.format("Apply default configuration."));
    if (this.configuration.getKeys(false).isEmpty()) {
      this.configuration.createSection("example-pattern");
      this.configuration.getConfigurationSection("example-pattern").set("pattern", "[hello]");
      this.configuration.getConfigurationSection("example-pattern").set("append-location", "end");
      this.configuration.getConfigurationSection("example-pattern").set("replacements", Arrays.asList("bonjour", "gutentag"));
    }
    this.save();
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.chatreplace.PatternConfiguration#setPatterns()
   */
  public void setPatterns() {
    for (final String node : this.configuration.getKeys(false)) {
      final String pattern = this.configuration.getString(node + ".pattern");
      final String appendAt = this.configuration.getString(node + ".append-location");
      final List<?> values = this.configuration.getStringList(node + ".replacements");
      final AppendPattern newPattern = new AppendPattern(pattern, values, AppendPattern.Location.valueOf(appendAt.toUpperCase()));
      this.patterns.add(newPattern);
    }
  }

}
