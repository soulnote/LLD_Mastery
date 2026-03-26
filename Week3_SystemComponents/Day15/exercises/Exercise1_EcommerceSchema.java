/**
 * EXERCISE 1: E-Commerce Database Schema Design
 * 
 * TASK: Design and normalize a database schema for an e-commerce system
 * 
 * Requirements:
 * 1. Identify all entities (Users, Products, Orders, Reviews, Categories, etc.)
 * 2. Define relationships between entities
 * 3. Apply normalization up to 3NF
 * 4. Design appropriate primary keys and foreign keys
 * 5. Consider indexing strategy for performance
 * 
 * DIFFICULTY: ⭐⭐⭐☆☆ (SDE2-Level)
 * TIME: 45 minutes
 */

public class Exercise1_EcommerceSchema {
    
    /*
     * TODO: Design the database schema
     * 
     * Consider these entities:
     * - Users (customers and sellers)
     * - Products
     * - Categories
     * - Orders
     * - Order Items
     * - Reviews
     * - Payments
     * - Shipping
     * - Addresses
     * 
     * Think about:
     * - One-to-many and many-to-many relationships
     * - Normalization (1NF, 2NF, 3NF)
     * - Denormalization for read performance
     * - Indexes for frequently queried columns
     */
    
    public static void main(String[] args) {
        System.out.println("📝 Exercise 1: E-Commerce Database Schema\n");
        System.out.println("Design a normalized database schema for an e-commerce platform.");
        System.out.println("\nEntities to consider:");
        System.out.println("- Users (buyers and sellers)");
        System.out.println("- Products");
        System.out.println("- Categories");
        System.out.println("- Orders");
        System.out.println("- Order Items");
        System.out.println("- Reviews");
        System.out.println("- Payments");
        System.out.println("- Shipping");
        System.out.println("- Addresses");
        
        System.out.println("\n📋 Your Schema Design:");
        System.out.println("----------------------------");
        
        // TODO: Document your schema design here
        
        // Example format:
        // Users(user_id, name, email, password_hash, role, created_at)
        // Products(product_id, seller_id, category_id, name, description, price, stock, created_at)
        
        System.out.println("\n✅ Complete the schema design!");
    }
}
