/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * PlayerListener.java is part of ChatReplace.
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
package name.richardson.james.chatreplace;

import java.util.Set;

import org.bukkit.event.player.PlayerChatEvent;


public class PlayerListener extends org.bukkit.event.player.PlayerListener {
  
  private final Set<ChatFormatter> formatters;
  
  public PlayerListener(Set<ChatFormatter> formatters) {
    this.formatters = formatters;
  }

  public void onPlayerChat (PlayerChatEvent event) {
    if (!event.isCancelled()) {
      String message = event.getMessage();
      for (ChatFormatter formatter : formatters) {
        message = formatter.format(message);
      }
      event.setMessage(message);
    }
  }
  
}
