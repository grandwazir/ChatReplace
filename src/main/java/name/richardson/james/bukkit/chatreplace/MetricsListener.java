package name.richardson.james.bukkit.chatreplace;

import java.io.IOException;

import name.richardson.james.bukkit.utilities.metrics.AbstractMetricsListener;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Graph;
import name.richardson.james.bukkit.utilities.metrics.Metrics.Plotter;

public class MetricsListener extends AbstractMetricsListener {

  public MetricsListener(ChatReplace plugin) throws IOException {
    super(plugin);
    this.setupUsageStatistics(plugin);
    this.metrics.start();
  }

  private void setupUsageStatistics(final ChatReplace plugin) {
    // Create a graph to show the total amount of kits issued.
    Graph graph = this.metrics.createGraph("Usage Statistics");
    graph.addPlotter(new Plotter("Total patterns configured") {
      @Override
      public int getValue() {
        return plugin.getTotalPatterns();
      }
    });
  }
  
  
}
