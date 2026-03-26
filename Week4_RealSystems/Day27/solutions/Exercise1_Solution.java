/**
 * Day 27 - Solution: Analytics Dashboard
 */
import java.util.*;

class AnalyticsSolution {
    private Map<String, Integer> metrics = new HashMap<>();
    
    public void track(String event) {
        metrics.put(event, metrics.getOrDefault(event, 0) + 1);
    }
    
    public Map<String, Integer> getMetrics() {
        return new HashMap<>(metrics);
    }
    
    public static void main(String[] args) {
        AnalyticsSolution a = new AnalyticsSolution();
        a.track("view");
        System.out.println(a.getMetrics());
    }
}
