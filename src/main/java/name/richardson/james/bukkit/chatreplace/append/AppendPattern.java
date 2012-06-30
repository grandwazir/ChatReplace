/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * AppendPattern.java is part of ChatReplace.
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
package name.richardson.james.bukkit.chatreplace.append;

import java.util.List;
import java.util.Random;
import java.util.regex.PatternSyntaxException;

import name.richardson.james.bukkit.chatreplace.Pattern;

public class AppendPattern extends Pattern {

  public enum Location {
    START,
    END
  }
  
  /** The location to append any alterations. */
  private Location location;
  
  /**
   * Instantiates a new pattern.
   *
   * @param pattern the regular expression as a String
   * @param values the values to use in case of a match
   * @param appendAt the location to append any matches
   */
  public AppendPattern (String pattern, List<?> values, Location appendAt) throws PatternSyntaxException {
    super(pattern, values);
    this.location = appendAt;
  }

  /* (non-Javadoc)
   * @see name.richardson.james.bukkit.chatreplace.Pattern#getValue()
   */
  public String getValue() {
    return getRandomReplacement();
  }
  
  /**
   * Gets a random replacement from the value list.
   *
   * @return the random replacement
   */
  private String getRandomReplacement() {
    Integer random = new Random().nextInt(values.size());
    return values.get(random).toString();
  }

  /**
   * Gets the location to append any matches.
   *
   * @return the append location
   */
  public Location getLocation() {
    return this.location;
  }
  
}
