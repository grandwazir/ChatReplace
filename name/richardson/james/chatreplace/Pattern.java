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
package name.richardson.james.chatreplace;

import java.util.List;
import java.util.regex.Matcher;

import name.richardson.james.chatreplace.util.Logger;

public abstract class Pattern {

    protected java.util.regex.Pattern pattern;
    protected List<?> values;
  
    public Pattern (String pattern, List<?> values) {
      if (pattern == null || values == null) throw new IllegalArgumentException();
      this.pattern = java.util.regex.Pattern.compile(pattern, java.util.regex.Pattern.CASE_INSENSITIVE);
      this.values = values;
      Logger.debug("Creating new pattern with pattern : " + getPattern() + " and values " + values.toString());
    }

    public boolean matches(String message) {
      Matcher matcher = pattern.matcher(message);
      return matcher.find();
    }
  
    protected abstract String getValue();
    
    public String getPattern() {
      return this.pattern.toString();
    }

}
