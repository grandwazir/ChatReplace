package name.richardson.james.bukkit.chatreplace.append;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import name.richardson.james.bukkit.chatreplace.PatternConfiguration;
import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.configuration.AbstractConfiguration;


public class AppendPatternConfiguration extends AbstractConfiguration implements PatternConfiguration {

  private final Set<AppendPattern> patterns = new LinkedHashSet<AppendPattern>();

  public AppendPatternConfiguration(Plugin plugin, String fileName) throws IOException {
    super(plugin, fileName);
    this.setPatterns();
  }
  
  public void setPatterns() {
    for (String node : configuration.getKeys(false)) {
      String pattern = configuration.getString(node + ".pattern");
      String appendAt = configuration.getString(node + ".append-location");
      List<?> values = configuration.getStringList(node + ".replacements");
      AppendPattern newPattern = new AppendPattern(pattern, values, appendAt);
      patterns.add(newPattern);
    }
  }
  
  public Set<AppendPattern> getPatterns() {
    return Collections.unmodifiableSet(patterns);
  }
  
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
