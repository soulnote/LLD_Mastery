/**
 * Test class for Day 2: Polymorphism & Abstraction
 * Demonstrates all key concepts with practical examples
 */
public class TestDay2 {
    public static void main(String[] args) {
        System.out.println("🎭 Day 2: Polymorphism & Abstraction Demo\n");
        
        // ===== POLYMORPHISM DEMO =====
        System.out.println("=== POLYMORPHISM DEMO: Payment Processing ===");
        
        PaymentGateway gateway = new PaymentGateway();
        
        // Create different payment processors
        PaymentProcessor creditCard = new CreditCardPayment("MERCH001", "1234567890123456", "John Doe");
        PaymentProcessor paypal = new PayPalPayment("MERCH001", "john.doe@email.com");
        PaymentProcessor crypto = new CryptoPayment("MERCH001", "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa", "Bitcoin");
        
        // Polymorphism: Same interface, different implementations
        double amount = 100.0;
        gateway.processTransaction(creditCard, amount);
        gateway.processTransaction(paypal, amount);
        gateway.processTransaction(crypto, amount);
        
        System.out.println("\n" + "=".repeat(60) + "\n");
        
        // ===== ABSTRACTION DEMO =====
        System.out.println("=== ABSTRACTION DEMO: Media Player ===");
        
        // Create different media types
        AudioTrack song = new AudioTrack("Bohemian Rhapsody", "Queen", 5.9, "MP3");
        VideoFile movie = new VideoFile("Inception", "Christopher Nolan", 148.0, "4K", "H.264");
        
        // Method overloading demonstration
        System.out.println("📋 Media Information:");
        song.displayInfo(); // Basic info
        song.displayInfo(true); // Detailed info
        song.displayInfo("json"); // JSON format
        
        System.out.println();
        movie.displayInfo(true);
        
        System.out.println("\n🎵 Audio Controls:");
        song.play();
        song.setVolume(75);
        song.mute();
        song.play(); // Show muted status
        song.unmute();
        song.pause();
        song.play(); // Resume
        song.stop();
        
        System.out.println("\n🎬 Video Controls:");
        movie.play();
        movie.changeResolution("1080p");
        movie.pause();
        movie.stop();
        
        // ===== COMPOSITION & POLYMORPHISM =====
        System.out.println("\n=== PLAYLIST DEMO: Composition + Polymorphism ===");
        
        Playlist myPlaylist = new Playlist("My Favorites");
        myPlaylist.addMedia(song);
        myPlaylist.addMedia(movie);
        myPlaylist.addMedia(new AudioTrack("Stairway to Heaven", "Led Zeppelin", 8.0, "FLAC"));
        
        myPlaylist.showPlaylist();
        
        System.out.println("\n🎵 Playing entire playlist...");
        myPlaylist.playAll();
        
        System.out.println("\n🎉 Day 2 concepts demonstrated successfully!");
        System.out.println("💡 Key Learning: Polymorphism enables flexibility, Abstraction hides complexity!");
        
        // ===== RUNTIME POLYMORPHISM DEMO =====
        System.out.println("\n=== RUNTIME POLYMORPHISM DEMO ===");
        demonstrateRuntimePolymorphism();
    }
    
    // Method to demonstrate runtime polymorphism
    private static void demonstrateRuntimePolymorphism() {
        // Array of different payment processors
        PaymentProcessor[] processors = {
            new CreditCardPayment("MERCH002", "9876543210987654", "Alice Smith"),
            new PayPalPayment("MERCH002", "alice@example.com"),
            new CryptoPayment("MERCH002", "bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh", "Ethereum")
        };
        
        System.out.println("🔄 Processing payments with runtime polymorphism:");
        
        // Same method call, different behavior based on actual object type
        for (PaymentProcessor processor : processors) {
            System.out.println("\n💳 Payment Method: " + processor.getPaymentMethod());
            System.out.println("💰 Transaction Fee for $50: $" + 
                String.format("%.2f", processor.getTransactionFee(50.0)));
        }
        
        System.out.println("\n✨ This is the power of polymorphism - one interface, many implementations!");
    }
}