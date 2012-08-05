/*******************************************************************************
 * Copyright (c) 2012 James Richardson.
 * 
 * Pattern.java is part of ChatReplace.
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
import java.util.regex.Matcher;

public abstract class AbstractPattern {

  /** The java regex pattern. */
  protected java.util.regex.Pattern pattern;

  /** The values used to replace any matches. */
  protected List<?> values;

  private final String permissionName;

  /**
   * Instantiates a new pattern.
   * 
   * @param pattern the regular expression as a String
   * @param values the values to use in case of a match
   */
  public AbstractPattern(final String pattern, final List<?> values, final String permissionName) {
    if ((pattern == null) || (values == null)) {
      throw new IllegalArgumentException();
    }
    this.pattern = java.util.regex.Pattern.compile(pattern, java.util.regex.Pattern.CASE_INSENSITIVE);
    this.values = values;
    this.permissionName = permissionName;
  }

  /**
   * Gets a string representation of this regular expression.
   * 
   * @return the pattern
   */
  public String getPattern() {
    return this.pattern.toString();
  }

  public String getPermissionName() {
    return this.permissionName;
  }

  /**
   * Gets the value to use when the pattern is matched. This is intended to be
   * overridden by sub-classes.
   * 
   * @return the value
   */
  public abstract String getValue();

  /**
   * Check if a given String matches this pattern.
   * 
   * @param message the String to match
   * @return true if successful. false if no match was found.
   */
  public boolean matches(final String message) {
    final Matcher matcher = this.pattern.matcher(message);
    return matcher.find();
  }

}
