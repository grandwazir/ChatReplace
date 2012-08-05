package name.richardson.james.bukkit.chatreplace;

import java.util.Collections;
import java.util.List;

import org.bukkit.permissions.Permissible;

import name.richardson.james.bukkit.utilities.permissions.PermissionManager;

public abstract class AbstractChatFormatter implements ChatFormatter {

  private final List<? extends AbstractPattern> patterns;

  private final PermissionManager permissions;

  public AbstractChatFormatter(final PatternConfiguration configuration, final PermissionManager permissions) {
    this.patterns = configuration.getPatterns();
    this.permissions = permissions;
  }

  public int getPatternCount() {
    return this.patterns.size();
  }

  public List<? extends AbstractPattern> getPatterns() {
    return Collections.unmodifiableList(this.patterns);
  }

  public boolean testPermission(final AbstractPattern pattern, final Permissible player) {
    if (pattern.getPermissionName() == null) {
      return true;
    }
    return this.permissions.hasPlayerPermission(player, pattern.getPermissionName());
  }

}
