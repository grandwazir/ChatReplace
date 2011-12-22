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

import java.io.IOException;
import java.util.Set;

import name.richardson.james.bukkit.util.Logger;
import name.richardson.james.chatreplace.ChatFormatter;

public class AppendChatFormatter implements ChatFormatter {
  
  private Set<AppendPattern> patterns;
  private final AppendPatternConfiguration configuration;
  private final Logger logger = new Logger(this.getClass());
  
  public AppendChatFormatter(AppendPatternConfiguration configuration) throws IOException {
    this.configuration = configuration;
    this.patterns = configuration.getPatterns();
    logger.info(String.format("%d append pattern(s) loaded.", patterns.size()));
  }

  @Override
  public String format(String message) {
    logger.debug("Formatting messsage: " + message);
    for (AppendPattern pattern : patterns) {
      if (pattern.matches(message)) {
        if (pattern.getAppendLocation().equalsIgnoreCase("end")) {
          message = message + " " + pattern.getValue();
        } else if (pattern.getAppendLocation().equalsIgnoreCase("start")) {
          message = pattern.getValue() + " " + message;
        }
        logger.debug("Match found for pattern: " + pattern.getPattern());
      }
    }
    logger.debug("Returning formatted message: " + message);
    return message;
  }

  @Override
  public void reload() {
    patterns.clear();
    this.configuration.load();
    this.patterns = this.configuration.getPatterns();
  }
  
  @Override
  public int getPatternCount() {
    return this.patterns.size();
  }
  
}
