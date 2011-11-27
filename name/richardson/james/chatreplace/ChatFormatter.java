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
package name.richardson.james.chatreplace;

import java.io.File;
import java.io.IOException;

import name.richardson.james.chatreplace.util.Logger;

import org.bukkit.configuration.file.YamlConfiguration;


public abstract class ChatFormatter {

  protected File configurationFile;
  protected YamlConfiguration configuration;
  
  public ChatFormatter(File configurationFile) throws IOException {
    this.configurationFile = configurationFile;
    Logger.info("Loading configuration: " + this.configurationFile.getName());
    this.configuration = YamlConfiguration.loadConfiguration(configurationFile);
    setConfigurationDefaults();
    configuration.save(configurationFile);
  }
  
  abstract protected void setConfigurationDefaults() throws IOException;
  
  abstract protected String format(String message);
  
}
