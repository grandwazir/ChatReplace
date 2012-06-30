/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * ChatFormatter.java is part of ChatReplace.
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
package name.richardson.james.bukkit.chatreplace;

public interface ChatFormatter {
  
  /**
   * Format a given chat message and return any alterations.
   *
   * @param message the chat message to format
   * @return the string to return
   */
  abstract String format(String message);
  
  /**
   * Reload patterns from the relevant configuration file.
   */
  abstract void reload();
  
  /**
   * Gets the total number of pattern attached to this formatter.
   *
   * @return the pattern count
   */
  abstract int getPatternCount();
  
}
