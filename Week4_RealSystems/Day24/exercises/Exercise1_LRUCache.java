/**
 * Day 24 - Exercise: Design an LRU Cache
 * 
 * Design an LRU (Least Recently Used) cache with the following features:
 * 1. Thread-safe operations
 * 2. Configurable capacity
 * 3. Automatic eviction of least recently used items
 * 4. O(1) get and put operations
 * 
 * Expected Duration: 45 minutes
 * Difficulty: ⭐⭐⭐☆☆
 */

public class Exercise1_LRUCache {
    
    public static void main(String[] args) {
        System.out.println("=== LRU Cache Exercise ===");
        
        // TODO: Implement your solution here
        
        System.out.println("\nExercise: Design an LRU Cache");
        System.out.println("Requirements:");
        System.out.println("1. Implement get(key) - O(1) time complexity");
        System.out.println("2. Implement put(key, value) - O(1) time complexity");
        System.out.println("3. Evict least recently used item when capacity is reached");
        System.out.println("4. Thread-safe operations");
        
        // Example test case structure:
        // LRUCache cache = new LRUCache(2);
        // cache.put(1, "value1");
        // cache.put(2, "value2");
        // System.out.println(cache.get(1)); // returns "value1"
        // cache.put(3, "value3"); // evicts key 2
        // System.out.println(cache.get(2)); // returns null
    }
}

// TODO: Implement the LRUCache class
// Hint: Use LinkedHashMap with accessOrder=true

class LRUCache {
    // TODO: Add your implementation here
    
    public LRUCache(int capacity) {
        // TODO: Initialize the cache with given capacity
    }
    
    public String get(int key) {
        // TODO: Return value if key exists, null otherwise
        return null;
    }
    
    public void put(int key, String value) {
        // TODO: Add or update key-value pair
        // Evict LRU item if capacity is exceeded
    }
}
