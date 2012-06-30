package name.richardson.james.bukkit.chatreplace;

import java.util.Set;

public interface PatternConfiguration {

  /**
   * Gets the patterns created from this configuration file.
   * 
   * @return the patterns
   */
  public Set<? extends Pattern> getPatterns();

  /**
   * Create the patterns from this configuration file.
   */
  public void setPatterns();

}
