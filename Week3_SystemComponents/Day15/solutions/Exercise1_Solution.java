/**
 * SOLUTION: E-Commerce Database Schema Design
 * 
 * Complete normalized schema for e-commerce platform
 */

public class Exercise1_Solution {
    
    /*
     * DATABASE SCHEMA DESIGN - E-Commerce Platform
     * ===========================================
     * 
     * NORMALIZED TABLES (3NF):
     * ------------------------
     */
    
    // Users table - stores both buyers and sellers
    // Users(user_id PK, email UNIQUE, password_hash, name, role, created_at, updated_at)
    // Indexes: email (unique), role
    
    // Addresses table - multiple addresses per user
    // Addresses(address_id PK, user_id FK, address_type, street, city, state, zip_code, country, is_default)
    // Indexes: user_id
    
    // Categories table - hierarchical category structure
    // Categories(category_id PK, parent_id FK (self-ref), name, description, created_at)
    // Indexes: parent_id
    
    // Products table - items sold by sellers
    // Products(product_id PK, seller_id FK, category_id FK, name, description, price, stock, 
    //          is_active, created_at, updated_at)
    // Indexes: seller_id, category_id, is_active, (name + is_active) for search
    
    // ProductVariants table - different variants of a product
    // ProductVariants(variant_id PK, product_id FK, sku UNIQUE, size, color, price_modifier, stock)
    // Indexes: product_id, sku
    
    // Orders table
    // Orders(order_id PK, user_id FK, address_id FK, status, total_amount, shipping_cost,
    //        payment_method, created_at, updated_at)
    // Indexes: user_id, status, created_at
    
    // OrderItems table
    // OrderItems(order_item_id PK, order_id FK, product_id FK, variant_id FK,
    //            quantity, unit_price, total_price)
    // Indexes: order_id, product_id
    
    // Reviews table
    // Reviews(review_id PK, product_id FK, user_id FK, order_id FK, rating (1-5),
    //         comment, created_at)
    // Indexes: product_id, user_id, (product_id + rating) for analytics
    
    // Payments table
    // Payments(payment_id PK, order_id FK, payment_method, amount, status,
    //          transaction_id, created_at)
    // Indexes: order_id, transaction_id, status
    
    // Shippings table
    // Shippings(shipping_id PK, order_id FK, carrier, tracking_number, status,
    //           shipped_at, delivered_at)
    // Indexes: order_id, tracking_number
    
    /*
     * RELATIONSHIPS:
     * --------------
     * User 1 ---- * Address (one-to-many)
     * User 1 ---- * Order (one-to-many)
     * User 1 ---- * Review (one-to-many)
     * User 1 ---- * Product (seller) (one-to-many)
     * Category 1 ---- * Category (self-referential, parent-child)
     * Category 1 ---- * Product (one-to-many)
     * Product 1 ---- * ProductVariant (one-to-many)
     * Product 1 ---- * Review (one-to-many)
     * Order 1 ---- * OrderItem (one-to-many)
     * Order 1 ---- * Payment (one-to-one, technically one-to-many for payment history)
     * Order 1 ---- * Shipping (one-to-one)
     * 
     * MANY-TO-MANY RELATIONSHIPS:
     * ----------------------------
     * Product <-> Order (via OrderItems junction table)
     * 
     * PERFORMANCE CONSIDERATIONS:
     * ---------------------------
     * 1. Read replicas for query-heavy workloads
     * 2. Caching layer (Redis) for product catalog
     * 3. Denormalized views for analytics/reporting
     * 4. Sharding by user_id for horizontal scaling
     */
    
    public static void main(String[] args) {
        System.out.println("📝 E-Commerce Database Schema - SOLUTION\n");
        
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║          NORMALIZED DATABASE SCHEMA (3NF)                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📦 CORE TABLES:");
        System.out.println("---------------");
        System.out.println("• Users(user_id, email, password_hash, name, role, created_at)");
        System.out.println("• Addresses(address_id, user_id, address_type, street, city, state, zip, country)");
        System.out.println("• Categories(category_id, parent_id, name, description)");
        System.out.println("• Products(product_id, seller_id, category_id, name, price, stock)");
        
        System.out.println("\n📦 TRANSACTION TABLES:");
        System.out.println("----------------------");
        System.out.println("• Orders(order_id, user_id, address_id, status, total, created_at)");
        System.out.println("• OrderItems(order_item_id, order_id, product_id, quantity, price)");
        System.out.println("• Payments(payment_id, order_id, method, amount, status, transaction_id)");
        System.out.println("• Shippings(shipping_id, order_id, carrier, tracking_number, status)");
        
        System.out.println("\n📦 REVIEW TABLES:");
        System.out.println("-----------------");
        System.out.println("• Reviews(review_id, product_id, user_id, rating, comment, created_at)");
        
        System.out.println("\n🔗 RELATIONSHIPS:");
        System.out.println("-----------------");
        System.out.println("• User 1 → * Orders");
        System.out.println("• Order 1 → * OrderItems");
        System.out.println("• Product 1 → * Reviews");
        System.out.println("• Category (self-referential for hierarchy)");
        
        System.out.println("\n✅ Schema design complete - normalized to 3NF!");
    }
}
