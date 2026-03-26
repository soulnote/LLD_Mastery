/**
 * Day 23 - Exercise: Design a URL Shortener Service
 * 
 * Design a URL shortener service with the following features:
 * 1. Shorten a long URL to a unique short code
 * 2. Redirect from short URL to original URL
 * 3. Track click statistics
 * 4. Handle concurrent requests
 * 
 * Expected Duration: 45 minutes
 * Difficulty: ⭐⭐⭐☆☆
 */

public class Exercise1_URLShortener {
    
    public static void main(String[] args) {
        System.out.println("=== URL Shortener Exercise ===");
        
        // TODO: Implement your solution here
        
        System.out.println("\nExercise: Design a URL shortener service");
        System.out.println("Requirements:");
        System.out.println("1. Generate unique short codes for URLs");
        System.out.println("2. Store URL mappings (short code -> original URL)");
        System.out.println("3. Implement redirect functionality");
        System.out.println("4. Track click count for each URL");
        System.out.println("5. Handle concurrent requests thread-safely");
        
        // Example test case structure:
        // URLShortener shortener = new URLShortener();
        //
        // // Shorten a URL
        // String shortUrl = shortener.shorten("https://www.google.com");
        // System.out.println("Short URL: " + shortUrl);
        //
        // // Redirect
        // String original = shortener.redirect("abc123");
        // System.out.println("Original: " + original);
        //
        // // Get stats
        // long clicks = shortener.getClickCount("abc123");
        // System.out.println("Clicks: " + clicks);
    }
}

// TODO: Implement the URLShortener class with the following features:
// 1. shorten(String originalUrl) - Generate and return short URL
// 2. redirect(String shortCode) - Return original URL and increment click count
// 3. getClickCount(String shortCode) - Return number of clicks
// 4. Thread-safe operations

class URLShortener {
    // TODO: Add your implementation here
    
    public String shorten(String originalUrl) {
        // TODO: Implement URL shortening logic
        // - Generate unique short code
        // - Store mapping
        // - Return shortened URL
        return null;
    }
    
    public String redirect(String shortCode) {
        // TODO: Implement redirect logic
        // - Look up original URL
        // - Increment click count
        // - Return original URL
        return null;
    }
    
    public long getClickCount(String shortCode) {
        // TODO: Return click count for the given short code
        return 0;
    }
}
