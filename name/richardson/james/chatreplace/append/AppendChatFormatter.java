/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * AppendChatFormatter.java is part of ChatReplace.
 * 
 * ChatReplace is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 * 
 * ChatReplace is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License 
 * along with ChatReplace.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.chatreplace.append;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import name.richardson.james.chatreplace.ChatFormatter;
import name.richardson.james.chatreplace.util.Logger;


public class AppendChatFormatter extends ChatFormatter {
  
  private Set<AppendPattern> patterns = new HashSet<AppendPattern>();
  
  public AppendChatFormatter(File configurationFile) throws IOException {
    super(configurationFile);
    for (String node : configuration.getKeys(false)) {
      String pattern = configuration.getString(node + ".pattern");
      String appendAt = configuration.getString(node + ".append-location");
      List<?> values = configuration.getStringList(node + ".replacements");
      AppendPattern newPattern = new AppendPattern(pattern, values, appendAt);
      patterns.add(newPattern);
    }
    Logger.info(String.format("%d append pattern(s) loaded.", patterns.size()));
  }

  @Override
  protected void setConfigurationDefaults() throws IOException {
    configuration.addDefault("example-pattern.pattern", "[hello]");
    configuration.addDefault("example-pattern.append-location", "end");
    configuration.addDefault("example-pattern.replacements", Arrays.asList("bonjour", "gutentag"));
    // configuration.options().copyDefaults(true);
    configuration.save(configurationFile);
  }

  @Override
  protected String format(String message) {
    Logger.debug("formatting messsage: " + message);
    for (AppendPattern pattern : patterns) {
      if (pattern.matches(message)) {
        if (pattern.getAppendLocation().equalsIgnoreCase("end")) {
          message = message + " " + pattern.getValue();
        } else if (pattern.getAppendLocation().equalsIgnoreCase("start")) {
          message = pattern.getValue() + " " + message;
        }
        Logger.debug("match found for pattern: " + pattern.getPattern());
      }
    }
    Logger.debug("formatted message: " + message);
    return message;
  }
}
