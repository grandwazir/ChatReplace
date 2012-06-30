/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * SubstitutionChatFormatter.java is part of ChatReplace.
 * 
 * ChatReplace is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * ChatReplace is distributed in the hope that it will be useful, but WITHOUT
 * ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with ChatReplace. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.chatreplace.substitution;

import java.io.IOException;
import java.util.Set;

import name.richardson.james.bukkit.chatreplace.ChatFormatter;
import name.richardson.james.bukkit.utilities.internals.Logger;

public class SubstitutionChatFormatter implements ChatFormatter {

  /** The logger for this class. */
  private static final Logger logger = new Logger(SubstitutionChatFormatter.class);

  /** The patterns associated with this formatter */
  private Set<SubstitutionPattern> patterns;

  /** The configuration from which the patterns were created. */
  private final SubstitutionPatternConfiguration configuration;

  /**
   * Instantiates a new append chat formatter.
   * 
   * @param configuration the configuration from which the patterns are created.
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public SubstitutionChatFormatter(final SubstitutionPatternConfiguration configuration) throws IOException {
    this.configuration = configuration;
    this.patterns = configuration.getPatterns();
    // logger.info(String.format("%d append pattern(s) loaded.",
    // patterns.size()));
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.chatreplace.ChatFormatter#format(java.lang
   * .String)
   */
  public String format(String message) {
    logger.debug("Formatting messsage: " + message);
    for (final SubstitutionPattern pattern : this.patterns) {
      if (pattern.matches(message)) {
        logger.debug("Match found for pattern: " + pattern.getPattern());
        message = message.replaceAll(pattern.getPattern(), pattern.getValue());
      }
    }
    logger.debug("Returning formatted message: " + message);
    return message;
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.chatreplace.ChatFormatter#getPatternCount()
   */
  public int getPatternCount() {
    return this.patterns.size();
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.chatreplace.ChatFormatter#reload()
   */
  public void reload() {
    this.patterns.clear();
    this.configuration.load();
    this.patterns = this.configuration.getPatterns();
  }

}
