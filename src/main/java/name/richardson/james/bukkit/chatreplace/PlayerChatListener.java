/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * PlayerChatListener.java is part of ChatReplace.
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
package name.richardson.james.bukkit.chatreplace;

import java.util.List;
import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatListener implements Listener {

  private final List<ChatFormatter> formatters;

  public PlayerChatListener(final List<ChatFormatter> formatters) {
    this.formatters = formatters;
  }

  @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
  public void onPlayerChat(final AsyncPlayerChatEvent event) {
    String message = event.getMessage();
    for (final ChatFormatter formatter : this.formatters) {
      message = formatter.format(message);
    }
    event.setMessage(message);
  }

}
