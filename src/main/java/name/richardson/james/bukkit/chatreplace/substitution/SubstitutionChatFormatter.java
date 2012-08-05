/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * SubstitutionChatFormatter.java is part of ChatReplace.
 * 
 * ChatReplace is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * ChatReplace is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * ChatReplace. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package name.richardson.james.bukkit.chatreplace.substitution;

import java.io.IOException;

import org.bukkit.permissions.Permissible;

import name.richardson.james.bukkit.chatreplace.AbstractChatFormatter;
import name.richardson.james.bukkit.chatreplace.AbstractPattern;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

public class SubstitutionChatFormatter extends AbstractChatFormatter {

  public SubstitutionChatFormatter(final SubstitutionPatternConfiguration configuration, final PermissionManager permissions) throws IOException {
    super(configuration, permissions);
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.chatreplace.ChatFormatter#format(java.lang
   * .String)
   */
  public String format(final Permissible player, String message) {
    for (final AbstractPattern pattern : this.getPatterns()) {
      if (!this.testPermission(pattern, player)) {
        continue;
      }
      if (pattern.matches(message)) {
        message = message.replaceAll(pattern.getPattern(), pattern.getValue());
      }
    }
    return message;
  }

}
