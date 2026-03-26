/**
 * Day 27 - Example: Analytics Dashboard
 */
import java.util.*;

class AnalyticsDashboard {
    private Map<String, Integer> metrics = new HashMap<>();
    
    public void track(String event) {
        metrics.put(event, metrics.getOrDefault(event, 0) + 1);
    }
    
    public Map<String, Integer> getMetrics() {
        return new HashMap<>(metrics);
    }
    
    public static void main(String[] args) {
        AnalyticsDashboard dashboard = new AnalyticsDashboard();
        dashboard.track("page_view");
        dashboard.track("click");
        System.out.println(dashboard.getMetrics());
    }
}
