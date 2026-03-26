/**
 * Day 23 - Solution: URL Shortener Service
 * 
 * Complete implementation of a URL shortener with thread-safe operations.
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Exercise1_Solution {
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== URL Shortener Solution ===\n");
        
        URLShortener shortener = new URLShortener();
        
        // Test 1: Basic URL shortening
        System.out.println("--- Test 1: Basic Shortening ---");
        String shortUrl1 = shortener.shorten("https://www.google.com");
        System.out.println("Short URL: " + shortUrl1);
        
        String shortUrl2 = shortener.shorten("https://github.com");
        System.out.println("Short URL: " + shortUrl2);
        
        // Test 2: Redirect
        System.out.println("\n--- Test 2: Redirect ---");
        String code1 = shortener.extractCode(shortUrl1);
        String original = shortener.redirect(code1);
        System.out.println("Original URL: " + original);
        
        // Test 3: Click tracking
        System.out.println("\n--- Test 3: Click Tracking ---");
        for (int i = 0; i < 10; i++) {
            shortener.redirect(code1);
        }
        System.out.println("Click count: " + shortener.getClickCount(code1));
        
        // Test 4: Concurrent operations
        System.out.println("\n--- Test 4: Concurrent Operations ---");
        testConcurrentShortening(shortener);
        
        System.out.println("\n=== All Tests Passed ===");
    }
    
    private static void testConcurrentShortening(URLShortener shortener) throws InterruptedException {
        int numThreads = 20;
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            new Thread(() -> {
                String url = "https://example.com/page" + index;
                String shortUrl = shortener.shorten(url);
                System.out.println("Thread " + index + ": " + shortUrl);
                latch.countDown();
            }).start();
        }
        
        latch.await();
    }
}

// ============================================================
// URL SHORTENER IMPLEMENTATION
// ============================================================

class URLShortener {
    private final Map<String, URLRecord> urlStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private static final String BASE_URL = "https://short.url/";
    
    // URL Record to store URL data
    static class URLRecord {
        final String originalUrl;
        final String shortCode;
        final long createdAt;
        final AtomicLong clickCount;
        
        URLRecord(String originalUrl, String shortCode) {
            this.originalUrl = originalUrl;
            this.shortCode = shortCode;
            this.createdAt = System.currentTimeMillis();
            this.clickCount = new AtomicLong(0);
        }
    }
    
    /**
     * Shortens a URL by generating a unique short code
     */
    public synchronized String shorten(String originalUrl) {
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            return null;
        }
        
        // Normalize URL
        originalUrl = originalUrl.trim();
        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "https://" + originalUrl;
        }
        
        // Generate unique short code
        String shortCode;
        do {
            long id = idGenerator.getAndIncrement();
            shortCode = encodeToBase62(id);
        } while (urlStore.containsKey(shortCode));
        
        // Store the URL
        URLRecord record = new URLRecord(originalUrl, shortCode);
        urlStore.put(shortCode, record);
        
        return BASE_URL + shortCode;
    }
    
    /**
     * Redirects to the original URL and increments click count
     */
    public String redirect(String shortCode) {
        URLRecord record = urlStore.get(shortCode);
        if (record != null) {
            record.clickCount.incrementAndGet();
            return record.originalUrl;
        }
        return null;
    }
    
    /**
     * Gets the click count for a short code
     */
    public long getClickCount(String shortCode) {
        URLRecord record = urlStore.get(shortCode);
        return record != null ? record.clickCount.get() : 0;
    }
    
    /**
     * Extracts short code from URL
     */
    public String extractCode(String shortUrl) {
        if (shortUrl != null && shortUrl.startsWith(BASE_URL)) {
            return shortUrl.substring(BASE_URL.length());
        }
        return shortUrl;
    }
    
    /**
     * Encodes a number to Base62 string
     */
    private String encodeToBase62(long num) {
        if (num == 0) return "0";
        
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        
        while (num > 0) {
            sb.append(chars.charAt((int) (num % 62)));
            num /= 62;
        }
        
        return sb.reverse().toString();
    }
}
