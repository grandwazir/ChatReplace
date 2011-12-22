package name.richardson.james.chatreplace;

import java.io.IOException;

import name.richardson.james.bukkit.util.Plugin;
import name.richardson.james.bukkit.util.configuration.AbstractConfiguration;

public class ChatReplaceConfiguration extends AbstractConfiguration {
  
  public ChatReplaceConfiguration(Plugin plugin) throws IOException {
    super(plugin, "config.yml");
  }

  public boolean getDebugging() {
    return configuration.getBoolean("debugging");
  }
  
  public boolean isAppending() {
    return configuration.getBoolean("formatters.append");
  }
  
  public boolean isSubstituting() {
    return configuration.getBoolean("formatters.substitution");
  }
  
}
