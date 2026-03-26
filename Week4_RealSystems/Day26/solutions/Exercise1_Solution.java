/**
 * Day 26 - Solution: Search System Implementation
 */
import java.util.*;

class SearchEngineSolution {
    private Map<String, Set<String>> index = new HashMap<>();
    
    public void addDocument(String id, String content) {
        for (String word : content.toLowerCase().split("\\s+")) {
            index.computeIfAbsent(word, k -> new HashSet<>()).add(id);
        }
    }
    
    public List<String> search(String query) {
        String[] terms = query.toLowerCase().split("\\s+");
        Set<String> results = new HashSet<>(index.getOrDefault(terms[0], Collections.emptySet()));
        for (int i = 1; i < terms.length; i++) {
            results.retainAll(index.getOrDefault(terms[i], Collections.emptySet()));
        }
        return new ArrayList<>(results);
    }
    
    public static void main(String[] args) {
        SearchEngineSolution engine = new SearchEngineSolution();
        engine.addDocument("1", "Java tutorial");
        engine.addDocument("2", "Python tutorial");
        System.out.println(engine.search("Java"));
    }
}
