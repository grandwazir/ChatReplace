package name.richardson.james.bukkit.chatreplace;

import java.util.Collections;
import java.util.List;

import org.bukkit.permissions.Permissible;

import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

public abstract class AbstractChatFormatter implements ChatFormatter {
  
  private final List<? extends AbstractPattern> patterns;
  
  private final PermissionManager permissions;

  public AbstractChatFormatter(PatternConfiguration configuration, PermissionManager permissions) {
    this.patterns = configuration.getPatterns();
    this.permissions = permissions;
  }
  
  public int getPatternCount() {
    return this.patterns.size();
  }
  
  public boolean testPermission(AbstractPattern pattern, Permissible player) {
    if (pattern.getPermissionName() == null) return true;
    return this.permissions.hasPlayerPermission(player, pattern.getPermissionName());
  }

  public List<? extends AbstractPattern> getPatterns() {
    return Collections.unmodifiableList(patterns);
  }

}
