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
  public SubstitutionPatternConfiguration(final ChatReplace plugin, final String fileName) throws IOException {
    super(plugin, fileName);
    this.setPatterns();
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.chatreplace.PatternConfiguration#getPatterns()
   */
  public Set<SubstitutionPattern> getPatterns() {
    return Collections.unmodifiableSet(this.patterns);
  }

  /**
   * Sets the defaults.
   * 
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void setDefaults() throws IOException {
    this.logger.debug(String.format("Apply default configuration."));
    if (this.configuration.getKeys(false).isEmpty()) {
      this.configuration.createSection("example-pattern");
      this.configuration.getConfigurationSection("example-pattern").set("pattern", "[hello]");
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
      final List<?> values = this.configuration.getStringList(node + ".replacements");
      final SubstitutionPattern newPattern = new SubstitutionPattern(pattern, values);
      this.patterns.add(newPattern);
    }
  }

}
