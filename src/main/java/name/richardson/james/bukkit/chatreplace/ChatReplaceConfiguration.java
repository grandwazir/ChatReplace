package name.richardson.james.bukkit.chatreplace;

import java.io.IOException;

import name.richardson.james.bukkit.utilities.configuration.PluginConfiguration;

public class ChatReplaceConfiguration extends PluginConfiguration {
  
  public ChatReplaceConfiguration(ChatReplace plugin) throws IOException {
    super(plugin);
  }
  
  public boolean isAppending() {
    return configuration.getBoolean("formatters.append");
  }
  
  public boolean isSubstituting() {
    return configuration.getBoolean("formatters.substitution");
  }
  
}
