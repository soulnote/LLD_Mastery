/**
 * Day 26 - Example: Search System Implementation
 * Demonstrates an inverted index for search.
 */

import java.util.*;

class SearchEngine {
    private Map<String, Set<String>> index = new HashMap<>();
    private Map<String, String> documents = new HashMap<>();
    
    public void addDocument(String id, String content) {
        documents.put(id, content);
        String[] words = content.toLowerCase().split("\\s+");
        for (String word : words) {
            index.computeIfAbsent(word, k -> new HashSet<>()).add(id);
        }
    }
    
    public List<String> search(String query) {
        String[] terms = query.toLowerCase().split("\\s+");
        if (terms.length == 0) return Collections.emptyList();
        
        Set<String> results = new HashSet<>(index.getOrDefault(terms[0], Collections.emptySet()));
        for (int i = 1; i < terms.length; i++) {
            Set<String> termDocs = index.getOrDefault(terms[i], Collections.emptySet());
            results.retainAll(termDocs);
        }
        return new ArrayList<>(results);
    }
    
    public static void main(String[] args) {
        SearchEngine engine = new SearchEngine();
        engine.addDocument("1", "Java programming tutorial");
        engine.addDocument("2", "Python machine learning");
        engine.addDocument("3", "JavaScript web development");
        
        System.out.println("Search 'Java': " + engine.search("Java"));
        System.out.println("Search 'programming': " + engine.search("programming"));
    }
}
