package name.richardson.james.chatreplace.substitution;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.configuration.AbstractConfiguration;


public class SubstitutionPatternConfiguration extends AbstractConfiguration {

  private final Set<SubstitutionPattern> patterns = new LinkedHashSet<SubstitutionPattern>();

  public SubstitutionPatternConfiguration(Plugin plugin, String fileName) throws IOException {
    super(plugin, fileName);
    this.setPatterns();
  }
  
  private void setPatterns() {
    for (String node : configuration.getKeys(false)) {
      String pattern = configuration.getString(node + ".pattern");
      List<?> values = configuration.getStringList(node + ".replacements");
      SubstitutionPattern newPattern = new SubstitutionPattern(pattern, values);
      patterns.add(newPattern);
    }
  }
  
  public Set<SubstitutionPattern> getPatterns() {
    return Collections.unmodifiableSet(patterns);
  }
  
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
