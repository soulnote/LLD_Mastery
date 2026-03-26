/**
 * Day 23 - Example: URL Shortener Implementation
 * Demonstrates a complete URL shortener service with Base62 encoding,
 * caching, and thread-safe operations.
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

// ============================================================
// 1. BASE62 ENCODER
// ============================================================

class Base62Encoder {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = 62;
    
    /**
     * Encodes a number to Base62 string
     * Example: 123456789 -> "2mrnK7"
     */
    public static String encode(long num) {
        if (num == 0) {
            return "0";
        }
        
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }
    
    /**
     * Decodes a Base62 string back to number
     * Example: "2mrnK7" -> 123456789
     */
    public static long decode(String encoded) {
        long result = 0;
        for (int i = 0; i < encoded.length(); i++) {
            result = result * BASE + BASE62.indexOf(encoded.charAt(i));
        }
        return result;
    }
}

// ============================================================
// 2. URL DATA MODEL
// ============================================================

class URLEntry {
    private final String shortCode;
    private final String originalURL;
    private final long createdAt;
    private final Long expiresAt;
    private final AtomicLong clickCount;
    
    public URLEntry(String shortCode, String originalURL, long createdAt, Long expiresAt) {
        this.shortCode = shortCode;
        this.originalURL = originalURL;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.clickCount = new AtomicLong(0);
    }
    
    public String getShortCode() { return shortCode; }
    public String getOriginalURL() { return originalURL; }
    public long getCreatedAt() { return createdAt; }
    public Long getExpiresAt() { return expiresAt; }
    public long getClickCount() { return clickCount.get(); }
    public void incrementClicks() { clickCount.incrementAndGet(); }
    
    public boolean isExpired() {
        return expiresAt != null && System.currentTimeMillis() > expiresAt;
    }
}

// ============================================================
// 3. URL SHORTENER SERVICE
// ============================================================

class URLShortenerService {
    private final Map<String, URLEntry> urlStore = new ConcurrentHashMap<>();
    private final Map<String, String> reverseIndex = new ConcurrentHashMap<>(); // original URL -> short code
    private final Map<String, String> cache = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    private static final String BASE_URL = "https://short.url/";
    private static final int CACHE_TTL = 3600000; // 1 hour in milliseconds
    
    /**
     * Shortens a URL
     * @param originalURL The URL to shorten
     * @return Shortened URL or null if URL is invalid
     */
    public String shorten(String originalURL) {
        // Validate URL
        if (originalURL == null || originalURL.trim().isEmpty()) {
            return null;
        }
        
        // Normalize URL
        originalURL = originalURL.trim();
        if (!originalURL.startsWith("http://") && !originalURL.startsWith("https://")) {
            originalURL = "https://" + originalURL;
        }
        
        // Check if already shortened (avoid duplicates)
        String existingShortCode = reverseIndex.get(originalURL);
        if (existingShortCode != null) {
            URLEntry entry = urlStore.get(existingShortCode);
            if (entry != null && !entry.isExpired()) {
                return BASE_URL + existingShortCode;
            }
        }
        
        // Generate unique short code
        String shortCode;
        do {
            long id = idGenerator.getAndIncrement();
            shortCode = Base62Encoder.encode(id);
        } while (urlStore.containsKey(shortCode));
        
        // Create and store URL entry
        URLEntry entry = new URLEntry(shortCode, originalURL, System.currentTimeMillis(), null);
        urlStore.put(shortCode, entry);
        reverseIndex.put(originalURL, shortCode);
        
        // Add to cache
        cache.put(shortCode, originalURL);
        
        return BASE_URL + shortCode;
    }
    
    /**
     * Shortens a URL with custom code
     * @param originalURL The URL to shorten
     * @param customCode Custom short code
     * @return Shortened URL or null if custom code is taken
     */
    public String shortenWithCustomCode(String originalURL, String customCode) {
        if (customCode == null || customCode.length() < 3 || customCode.length() > 10) {
            return null;
        }
        
        // Check if custom code is available
        if (urlStore.containsKey(customCode)) {
            return null;
        }
        
        // Validate URL
        if (originalURL == null || originalURL.trim().isEmpty()) {
            return null;
        }
        
        originalURL = originalURL.trim();
        if (!originalURL.startsWith("http://") && !originalURL.startsWith("https://")) {
            originalURL = "https://" + originalURL;
        }
        
        // Create and store URL entry
        URLEntry entry = new URLEntry(customCode, originalURL, System.currentTimeMillis(), null);
        urlStore.put(customCode, entry);
        reverseIndex.put(originalURL, customCode);
        
        // Add to cache
        cache.put(customCode, originalURL);
        
        return BASE_URL + customCode;
    }
    
    /**
     * Gets the original URL from short code
     * @param shortCode The short code
     * @return Original URL or null if not found
     */
    public String getOriginalURL(String shortCode) {
        // Check cache first
        String cached = cache.get(shortCode);
        if (cached != null) {
            return cached;
        }
        
        // Look up in store
        URLEntry entry = urlStore.get(shortCode);
        if (entry == null || entry.isExpired()) {
            return null;
        }
        
        // Update cache
        cache.put(shortCode, entry.getOriginalURL());
        
        return entry.getOriginalURL();
    }
    
    /**
     * Records a click and returns the original URL
     * @param shortCode The short code
     * @return Original URL or null if not found
     */
    public String redirect(String shortCode) {
        URLEntry entry = urlStore.get(shortCode);
        if (entry == null || entry.isExpired()) {
            return null;
        }
        
        // Increment click count
        entry.incrementClicks();
        
        // Get original URL
        String originalURL = entry.getOriginalURL();
        
        // Update cache
        cache.put(shortCode, originalURL);
        
        return originalURL;
    }
    
    /**
     * Gets statistics for a short code
     * @param shortCode The short code
     * @return Statistics map or null if not found
     */
    public Map<String, Object> getStats(String shortCode) {
        URLEntry entry = urlStore.get(shortCode);
        if (entry == null) {
            return null;
        }
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("short_code", shortCode);
        stats.put("original_url", entry.getOriginalURL());
        stats.put("clicks", entry.getClickCount());
        stats.put("created_at", new Date(entry.getCreatedAt()));
        
        return stats;
    }
    
    /**
     * Deletes a shortened URL
     * @param shortCode The short code to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(String shortCode) {
        URLEntry entry = urlStore.remove(shortCode);
        if (entry != null) {
            reverseIndex.remove(entry.getOriginalURL());
            cache.remove(shortCode);
            return true;
        }
        return false;
    }
    
    public int getTotalURLs() {
        return urlStore.size();
    }
}

// ============================================================
// 4. DEMO & TEST
// ============================================================

public class URLShortenerExample {
    public static void main(String[] args) {
        System.out.println("=== URL Shortener Demo ===\n");
        
        URLShortenerService shortener = new URLShortenerService();
        
        // Test 1: Basic URL shortening
        System.out.println("--- Test 1: Basic URL Shortening ---");
        String shortUrl1 = shortener.shorten("https://www.google.com/search?q=java+tutorials");
        System.out.println("Shortened: " + shortUrl1);
        
        String shortUrl2 = shortener.shorten("https://github.com/search?q=system+design");
        System.out.println("Shortened: " + shortUrl2);
        
        // Test 2: Custom code
        System.out.println("\n--- Test 2: Custom Short Code ---");
        String customUrl = shortener.shortenWithCustomCode("https://stackoverflow.com", "so-link");
        System.out.println("Custom short URL: " + customUrl);
        
        // Test 3: Redirect and click tracking
        System.out.println("\n--- Test 3: Redirect & Click Tracking ---");
        String shortCode = shortUrl1.substring(BASE_URL.length());
        System.out.println("Short code: " + shortCode);
        
        // Simulate multiple redirects
        for (int i = 0; i < 5; i++) {
            String original = shortener.redirect(shortCode);
            if (i == 0) {
                System.out.println("Redirect #" + (i + 1) + " to: " + original);
            }
        }
        
        // Test 4: Get statistics
        System.out.println("\n--- Test 4: Statistics ---");
        Map<String, Object> stats = shortener.getStats(shortCode);
        System.out.println("Stats: " + stats);
        
        // Test 5: Concurrent URL shortening
        System.out.println("\n--- Test 5: Concurrent Operations ---");
        testConcurrentShortening(shortener);
        
        // Test 6: Get original URL
        System.out.println("\n--- Test 6: Get Original URL ---");
        String originalUrl = shortener.getOriginalURL(shortCode);
        System.out.println("Original URL: " + originalUrl);
        
        System.out.println("\n--- Total URLs: " + shortener.getTotalURLs() + " ---");
    }
    
    private static void testConcurrentShortening(URLShortenerService shortener) {
        int numThreads = 10;
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            new Thread(() -> {
                String url = "https://example.com/page" + index;
                String shortUrl = shortener.shorten(url);
                System.out.println("Thread " + index + " shortened: " + shortUrl);
                latch.countDown();
            }).start();
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
