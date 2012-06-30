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
  public AppendPatternConfiguration(ChatReplace plugin, String fileName) throws IOException {
    super(plugin, fileName);
    this.setPatterns();
  }
  
  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.chatreplace.PatternConfiguration#setPatterns()
   */
  public void setPatterns() {
    for (String node : configuration.getKeys(false)) {
      String pattern = configuration.getString(node + ".pattern");
      String appendAt = configuration.getString(node + ".append-location");
      List<?> values = configuration.getStringList(node + ".replacements");
      AppendPattern newPattern = new AppendPattern(pattern, values, AppendPattern.Location.valueOf(appendAt.toUpperCase()));
      patterns.add(newPattern);
    }
  }
  
  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.chatreplace.PatternConfiguration#getPatterns()
   */
  public Set<AppendPattern> getPatterns() {
    return Collections.unmodifiableSet(patterns);
  }
  
  /**
   * Sets the defaults values for this configuration file.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void setDefaults() throws IOException {
    logger.debug(String.format("Apply default configuration."));
    if (configuration.getKeys(false).isEmpty()) {
      configuration.createSection("example-pattern");
      configuration.getConfigurationSection("example-pattern").set("pattern", "[hello]");
      configuration.getConfigurationSection("example-pattern").set("append-location", "end");
      configuration.getConfigurationSection("example-pattern").set("replacements", Arrays.asList("bonjour", "gutentag"));
    }
    this.save();
  }
  
  
}
