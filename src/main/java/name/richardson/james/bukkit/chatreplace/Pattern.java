/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * Pattern.java is part of ChatReplace.
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

import java.util.List;
import java.util.regex.Matcher;

public abstract class Pattern {

    /** The java regex pattern. */
    protected java.util.regex.Pattern pattern;
    
    /** The values used to replace any matches. */
    protected List<?> values;
  
    /**
     * Instantiates a new pattern.
     *
     * @param pattern the regular expression as a String
     * @param values the values to use in case of a match
     */
    public Pattern (String pattern, List<?> values) {
      if (pattern == null || values == null) throw new IllegalArgumentException();
      this.pattern = java.util.regex.Pattern.compile(pattern, java.util.regex.Pattern.CASE_INSENSITIVE);
      this.values = values;
    }

    /**
     * Check if a given String matches this pattern.
     *
     * @param message the String to match
     * @return true if successful. false if no match was found.
     */
    public boolean matches(String message) {
      Matcher matcher = pattern.matcher(message);
      return matcher.find();
    }
  
    /**
     * Gets the value to use when the pattern is matched. This is intended to be overridden by sub-classes.
     *
     * @return the value
     */
    protected abstract String getValue();
    
    /**
     * Gets a string representation of this regular expression.
     *
     * @return the pattern
     */
    public String getPattern() {
      return this.pattern.toString();
    }

}
