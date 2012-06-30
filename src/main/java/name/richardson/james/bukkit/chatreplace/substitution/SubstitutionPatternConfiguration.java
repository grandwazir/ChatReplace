package name.richardson.james.bukkit.chatreplace.substitution;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.chatreplace.ChatReplace;
import name.richardson.james.bukkit.utilities.persistence.YAMLStorage;

public class SubstitutionPatternConfiguration extends YAMLStorage {

  /** The patterns created from this configuration file. */
  private final Set<SubstitutionPattern> patterns = new LinkedHashSet<SubstitutionPattern>();

  /**
   * Instantiates a new append pattern configuration.
   *
   * @param plugin the plugin
   * @param fileName the file name
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public SubstitutionPatternConfiguration(ChatReplace plugin, String fileName) throws IOException {
    super(plugin, fileName);
    this.setPatterns();
  }
  
  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.chatreplace.PatternConfiguration#setPatterns()
   */
  public void setPatterns() {
    for (String node : configuration.getKeys(false)) {
      String pattern = configuration.getString(node + ".pattern");
      List<?> values = configuration.getStringList(node + ".replacements");
      SubstitutionPattern newPattern = new SubstitutionPattern(pattern, values);
      patterns.add(newPattern);
    }
  }
  
  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.chatreplace.PatternConfiguration#getPatterns()
   */
  public Set<SubstitutionPattern> getPatterns() {
    return Collections.unmodifiableSet(patterns);
  }
  
  /**
   * Sets the defaults.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void setDefaults() throws IOException {
    logger.debug(String.format("Apply default configuration."));
    if (configuration.getKeys(false).isEmpty()) {
      configuration.createSection("example-pattern");
      configuration.getConfigurationSection("example-pattern").set("pattern", "[hello]");
      configuration.getConfigurationSection("example-pattern").set("replacements", Arrays.asList("bonjour", "gutentag"));
    }
    this.save();
  }
  
}
