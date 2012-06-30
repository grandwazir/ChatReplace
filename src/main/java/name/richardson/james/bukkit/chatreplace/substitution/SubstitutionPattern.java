/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * SubstitutionPattern.java is part of ChatReplace.
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
package name.richardson.james.bukkit.chatreplace.substitution;

import java.util.List;
import java.util.Random;
import java.util.regex.PatternSyntaxException;

import name.richardson.james.bukkit.chatreplace.Pattern;

public class SubstitutionPattern extends Pattern {

  public SubstitutionPattern (String pattern, List<?> values) throws PatternSyntaxException {
    super(pattern, values);
  }

  public String getValue() {
    return getRandomReplacement();
  }
  
  private String getRandomReplacement() {
    Integer random = new Random().nextInt(values.size());
    return values.get(random).toString();
  }
  
}