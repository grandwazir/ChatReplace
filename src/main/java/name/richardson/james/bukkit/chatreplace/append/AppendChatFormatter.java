/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * AppendChatFormatter.java is part of ChatReplace.
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
package name.richardson.james.bukkit.chatreplace.append;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.bukkit.permissions.Permissible;

import name.richardson.james.bukkit.chatreplace.AbstractChatFormatter;
import name.richardson.james.bukkit.chatreplace.AbstractPattern;
import name.richardson.james.bukkit.chatreplace.ChatFormatter;
import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

public class AppendChatFormatter extends AbstractChatFormatter {

  public AppendChatFormatter(final AppendPatternConfiguration configuration, PermissionManager permissions) throws IOException {
    super(configuration, permissions);
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.chatreplace.ChatFormatter#format(java.lang
   * .String)
   */
  public String format(Permissible player, String message) {
    final StringBuilder formattedMessage = new StringBuilder(message);
    for (final AbstractPattern pattern : this.getPatterns()) {
      if (!this.testPermission(pattern, player)) continue;
      AppendPattern appendPattern = (AppendPattern) pattern;
      if (pattern.matches(message)) {
        switch (appendPattern.getLocation()) {
        case END:
          formattedMessage.append(" ");
          formattedMessage.append(appendPattern.getValue());
          break;
        case START:
          formattedMessage.insert(0, " ");
          formattedMessage.insert(0, appendPattern.getValue());
          break;
        }
      }
    }
    message = formattedMessage.toString();
    return message;
  }

}
