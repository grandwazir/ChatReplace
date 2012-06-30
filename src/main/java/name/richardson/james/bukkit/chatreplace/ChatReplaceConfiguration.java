package name.richardson.james.bukkit.chatreplace;

import java.io.IOException;

import name.richardson.james.bukkit.utilities.configuration.PluginConfiguration;

public class ChatReplaceConfiguration extends PluginConfiguration {

  public ChatReplaceConfiguration(final ChatReplace plugin) throws IOException {
    super(plugin);
  }

  public boolean isAppending() {
    return this.configuration.getBoolean("formatters.append");
  }

  public boolean isSubstituting() {
    return this.configuration.getBoolean("formatters.substitution");
  }

}
