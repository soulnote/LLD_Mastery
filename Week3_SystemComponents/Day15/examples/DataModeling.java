/**
 * EXAMPLE: Data Modeling - E-Commerce System
 * 
 * This example demonstrates production-quality data modeling for an e-commerce platform.
 * It shows entity definitions, relationships, and schema design patterns.
 * 
 * @author LLD Mastery
 * @version 1.0
 */

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DataModeling {
    
    // ============================================================
    // CORE ENTITIES
    // ============================================================
    
    /**
     * User entity - represents both customers and sellers in the system
     */
    static class User {
        private final int userId;
        private String email;
        private String name;
        private String passwordHash;
        private UserRole role;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private boolean isActive;
        
        // Relationships
        private List<Address> addresses = new ArrayList<>();
        private List<Order> orders = new ArrayList<>();
        private List<Review> reviews = new ArrayList<>();
        
        public User(int userId, String email, String name, UserRole role) {
            this.userId = userId;
            this.email = email;
            this.name = name;
            this.role = role;
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
            this.isActive = true;
            this.passwordHash = "";
        }
        
        // Getters and Setters
        public int getUserId() { return userId; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public UserRole getRole() { return role; }
        public void setRole(UserRole role) { this.role = role; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public LocalDateTime getUpdatedAt() { return updatedAt; }
        public boolean isActive() { return isActive; }
        public void setActive(boolean active) { isActive = active; }
        public List<Address> getAddresses() { return addresses; }
        public List<Order> getOrders() { return orders; }
        public List<Review> getReviews() { return reviews; }
        
        @Override
        public String toString() {
            return String.format("User{id=%d, email='%s', name='%s', role=%s}", 
                userId, email, name, role);
        }
    }
    
    /**
     * User roles in the system
     */
    enum UserRole {
        CUSTOMER,
        SELLER,
        ADMIN
    }
    
    /**
     * Address entity - supports multiple addresses per user
     */
    static class Address {
        private final int addressId;
        private final int userId;
        private AddressType addressType;
        private String street;
        private String city;
        private String state;
        private String zipCode;
        private String country;
        private boolean isDefault;
        
        public Address(int addressId, int userId, String street, String city) {
            this.addressId = addressId;
            this.userId = userId;
            this.street = street;
            this.city = city;
            this.addressType = AddressType.SHIPPING;
        }
        
        public int getAddressId() { return addressId; }
        public int getUserId() { return userId; }
        public AddressType getAddressType() { return addressType; }
        public String getStreet() { return street; }
        public String getCity() { return city; }
        public String getState() { return state; }
        public String getZipCode() { return zipCode; }
        public boolean isDefault() { return isDefault; }
    }
    
    enum AddressType {
        SHIPPING,
        BILLING
    }
    
    /**
     * Category entity - supports hierarchical categories
     */
    static class Category {
        private final int categoryId;
        private Integer parentCategoryId;  // Self-referential for hierarchy
        private String name;
        private String description;
        private List<Category> subcategories = new ArrayList<>();
        private List<Product> products = new ArrayList<>();
        
        public Category(int categoryId, String name) {
            this.categoryId = categoryId;
            this.name = name;
        }
        
        public int getCategoryId() { return categoryId; }
        public Integer getParentCategoryId() { return parentCategoryId; }
        public String getName() { return name; }
        public List<Category> getSubcategories() { return subcategories; }
        public List<Product> getProducts() { return products; }
    }
    
    /**
     * Product entity - items sold by sellers
     */
    static class Product {
        private final int productId;
        private int sellerId;
        private int categoryId;
        private String name;
        private String description;
        private double price;
        private int stockQuantity;
        private boolean isActive;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        // Relationships
        private Category category;
        private List<ProductVariant> variants = new ArrayList<>();
        private List<Review> reviews = new ArrayList<>();
        
        public Product(int productId, String name, double price) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.stockQuantity = 0;
            this.isActive = true;
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }
        
        // Getters and Setters
        public int getProductId() { return productId; }
        public int getSellerId() { return sellerId; }
        public void setSellerId(int sellerId) { this.sellerId = sellerId; }
        public int getCategoryId() { return categoryId; }
        public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public int getStockQuantity() { return stockQuantity; }
        public void setStockQuantity(int stockQuantity) { 
            this.stockQuantity = stockQuantity;
            this.updatedAt = LocalDateTime.now();
        }
        public boolean isActive() { return isActive; }
        public void setActive(boolean active) { isActive = active; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public Category getCategory() { return category; }
        public List<ProductVariant> getVariants() { return variants; }
        public List<Review> getReviews() { return reviews; }
        
        /**
         * Check if product is in stock
         */
        public boolean isInStock() {
            return isActive && stockQuantity > 0;
        }
        
        /**
         * Reduce stock after purchase
         */
        public boolean reduceStock(int quantity) {
            if (stockQuantity >= quantity) {
                stockQuantity -= quantity;
                updatedAt = LocalDateTime.now();
                return true;
            }
            return false;
        }
    }
    
    /**
     * Product Variant - different variants of a product (size, color, etc.)
     */
    static class ProductVariant {
        private final int variantId;
        private final int productId;
        private String sku;
        private String size;
        private String color;
        private double priceModifier;
        private int stockQuantity;
        
        public ProductVariant(int variantId, int productId, String sku) {
            this.variantId = variantId;
            this.productId = productId;
            this.sku = sku;
        }
        
        public int getVariantId() { return variantId; }
        public int getProductId() { return productId; }
        public String getSku() { return sku; }
        public String getSize() { return size; }
        public String getColor() { return color; }
        public double getPriceModifier() { return priceModifier; }
        public int getStockQuantity() { return stockQuantity; }
        
        public double getTotalPrice() {
            return priceModifier;
        }
    }
    
    /**
     * Order entity
     */
    static class Order {
        private final int orderId;
        private final int userId;
        private int addressId;
        private OrderStatus status;
        private double totalAmount;
        private double shippingCost;
        private PaymentMethod paymentMethod;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        // Relationships
        private List<OrderItem> items = new ArrayList<>();
        private Payment payment;
        private Shipping shipping;
        
        public Order(int orderId, int userId) {
            this.orderId = orderId;
            this.userId = userId;
            this.status = OrderStatus.PENDING;
            this.totalAmount = 0;
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }
        
        // Getters and Setters
        public int getOrderId() { return orderId; }
        public int getUserId() { return userId; }
        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { 
            this.status = status;
            this.updatedAt = LocalDateTime.now();
        }
        public double getTotalAmount() { return totalAmount; }
        public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public List<OrderItem> getItems() { return items; }
        
        /**
         * Add item to order and recalculate total
         */
        public void addItem(OrderItem item) {
            items.add(item);
            recalculateTotal();
        }
        
        /**
         * Recalculate order total
         */
        private void recalculateTotal() {
            totalAmount = items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
                .sum() + shippingCost;
        }
    }
    
    enum OrderStatus {
        PENDING,
        CONFIRMED,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }
    
    enum PaymentMethod {
        CREDIT_CARD,
        DEBIT_CARD,
        UPI,
        WALLET,
        NET_BANKING
    }
    
    /**
     * Order Item - junction between Order and Product
     */
    static class OrderItem {
        private final int orderItemId;
        private final int orderId;
        private final int productId;
        private Integer variantId;  // Nullable if no variant
        private int quantity;
        private double unitPrice;
        private double totalPrice;
        
        public OrderItem(int orderItemId, int orderId, int productId, int quantity, double unitPrice) {
            this.orderItemId = orderItemId;
            this.orderId = orderId;  // Now properly initialized via constructor
            this.productId = productId;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.totalPrice = quantity * unitPrice;
        }
        
        public int getOrderItemId() { return orderItemId; }
        public int getOrderId() { return orderId; }
        public int getProductId() { return productId; }
        public Integer getVariantId() { return variantId; }
        public int getQuantity() { return quantity; }
        public double getUnitPrice() { return unitPrice; }
        public double getTotalPrice() { return totalPrice; }
    }
    
    /**
     * Payment entity
     */
    static class Payment {
        private final int paymentId;
        private final int orderId;
        private PaymentMethod method;
        private double amount;
        private PaymentStatus status;
        private String transactionId;
        private LocalDateTime createdAt;
        
        public Payment(int paymentId, int orderId, double amount) {
            this.paymentId = paymentId;
            this.orderId = orderId;
            this.amount = amount;
            this.status = PaymentStatus.PENDING;
            this.createdAt = LocalDateTime.now();
        }
        
        public int getPaymentId() { return paymentId; }
        public int getOrderId() { return orderId; }
        public PaymentMethod getMethod() { return method; }
        public double getAmount() { return amount; }
        public PaymentStatus getStatus() { return status; }
        public void setStatus(PaymentStatus status) { this.status = status; }
        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    }
    
    enum PaymentStatus {
        PENDING,
        PROCESSING,
        SUCCESS,
        FAILED,
        REFUNDED
    }
    
    /**
     * Shipping entity
     */
    static class Shipping {
        private final int shippingId;
        private final int orderId;
        private String carrier;
        private String trackingNumber;
        private ShippingStatus status;
        private LocalDateTime shippedAt;
        private LocalDateTime deliveredAt;
        
        public Shipping(int shippingId, int orderId) {
            this.shippingId = shippingId;
            this.orderId = orderId;
            this.status = ShippingStatus.PENDING;
        }
        
        public int getShippingId() { return shippingId; }
        public int getOrderId() { return orderId; }
        public String getCarrier() { return carrier; }
        public void setCarrier(String carrier) { this.carrier = carrier; }
        public String getTrackingNumber() { return trackingNumber; }
        public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
        public ShippingStatus getStatus() { return status; }
        public void setStatus(ShippingStatus status) { this.status = status; }
    }
    
    enum ShippingStatus {
        PENDING,
        PICKED_UP,
        IN_TRANSIT,
        OUT_FOR_DELIVERY,
        DELIVERED,
        FAILED
    }
    
    /**
     * Review entity
     */
    static class Review {
        private final int reviewId;
        private final int productId;
        private final int userId;
        private final Integer orderId;  // Nullable - can review without order
        private int rating;  // 1-5
        private String comment;
        private LocalDateTime createdAt;
        
        public Review(int reviewId, int productId, int userId, int rating) {
            this.reviewId = reviewId;
            this.productId = productId;
            this.userId = userId;
            this.rating = rating;
            this.createdAt = LocalDateTime.now();
        }
        
        public int getReviewId() { return reviewId; }
        public int getProductId() { return productId; }
        public int getUserId() { return userId; }
        public int getRating() { return rating; }
        public void setRating(int rating) { 
            if (rating >= 1 && rating <= 5) {
                this.rating = rating;
            }
        }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
        public LocalDateTime getCreatedAt() { return createdAt; }
    }
    
    // ============================================================
    // DATABASE SCHEMA DOCUMENTATION
    // ============================================================
    
    /*
     * DATABASE SCHEMA - E-Commerce Platform
     * ======================================
     * 
     * NORMALIZED TABLES (3NF):
     * ------------------------
     * 
     * users(
     *   user_id PRIMARY KEY,
     *   email UNIQUE NOT NULL,
     *   password_hash NOT NULL,
     *   name NOT NULL,
     *   role NOT NULL DEFAULT 'CUSTOMER',
     *   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     *   updated_at TIMESTAMP,
     *   is_active BOOLEAN DEFAULT TRUE
     * )
     * 
     * addresses(
     *   address_id PRIMARY KEY,
     *   user_id FOREIGN KEY -> users(user_id),
     *   address_type NOT NULL,
     *   street NOT NULL,
     *   city NOT NULL,
     *   state,
     *   zip_code,
     *   country DEFAULT 'USA',
     *   is_default BOOLEAN DEFAULT FALSE
     * )
     * 
     * categories(
     *   category_id PRIMARY KEY,
     *   parent_category_id FOREIGN KEY -> categories(category_id),
     *   name NOT NULL,
     *   description
     * )
     * 
     * products(
     *   product_id PRIMARY KEY,
     *   seller_id FOREIGN KEY -> users(user_id),
     *   category_id FOREIGN KEY -> categories(category_id),
     *   name NOT NULL,
     *   description,
     *   price DECIMAL(10,2) NOT NULL,
     *   stock_quantity INTEGER DEFAULT 0,
     *   is_active BOOLEAN DEFAULT TRUE,
     *   created_at TIMESTAMP,
     *   updated_at TIMESTAMP
     * )
     * 
     * product_variants(
     *   variant_id PRIMARY KEY,
     *   product_id FOREIGN KEY -> products(product_id),
     *   sku UNIQUE NOT NULL,
     *   size,
     *   color,
     *   price_modifier DECIMAL(10,2) DEFAULT 0,
     *   stock_quantity DEFAULT 0
     * )
     * 
     * orders(
     *   order_id PRIMARY KEY,
     *   user_id FOREIGN KEY -> users(user_id),
     *   address_id FOREIGN KEY -> addresses(address_id),
     *   status NOT NULL,
     *   total_amount DECIMAL(10,2),
     *   shipping_cost DECIMAL(10,2) DEFAULT 0,
     *   payment_method,
     *   created_at TIMESTAMP,
     *   updated_at TIMESTAMP
     * )
     * 
     * order_items(
     *   order_item_id PRIMARY KEY,
     *   order_id FOREIGN KEY -> orders(order_id),
     *   product_id FOREIGN KEY -> products(product_id),
     *   variant_id FOREIGN KEY -> product_variants(variant_id),
     *   quantity INTEGER NOT NULL,
     *   unit_price DECIMAL(10,2) NOT NULL,
     *   total_price DECIMAL(10,2) NOT NULL
     * )
     * 
     * payments(
     *   payment_id PRIMARY KEY,
     *   order_id FOREIGN KEY -> orders(order_id),
     *   payment_method NOT NULL,
     *   amount DECIMAL(10,2) NOT NULL,
     *   status NOT NULL,
     *   transaction_id,
     *   created_at TIMESTAMP
     * )
     * 
     * shippings(
     *   shipping_id PRIMARY KEY,
     *   order_id FOREIGN KEY -> orders(order_id),
     *   carrier,
     *   tracking_number,
     *   status,
     *   shipped_at TIMESTAMP,
     *   delivered_at TIMESTAMP
     * )
     * 
     * reviews(
     *   review_id PRIMARY KEY,
     *   product_id FOREIGN KEY -> products(product_id),
     *   user_id FOREIGN KEY -> users(user_id),
     *   order_id FOREIGN KEY -> orders(order_id),
     *   rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
     *   comment,
     *   created_at TIMESTAMP
     * )
     * 
     * RELATIONSHIPS:
     * --------------
     * User 1 ---- * Address (one-to-many)
     * User 1 ---- * Order (one-to-many)
     * User 1 ---- * Review (one-to-many)
     * User 1 ---- * Product (seller) (one-to-many)
     * Category 1 ---- * Category (self-referential for hierarchy)
     * Category 1 ---- * Product (one-to-many)
     * Product 1 ---- * ProductVariant (one-to-many)
     * Product 1 ---- * Review (one-to-many)
     * Order 1 ---- * OrderItem (one-to-many)
     * Order 1 ---- * Payment (one-to-one)
     * Order 1 ---- * Shipping (one-to-one)
     * Product * ---- * Order (via OrderItems junction table)
     * 
     * INDEXES:
     * ---------
     * idx_user_email ON users(email)
     * idx_product_category ON products(category_id)
     * idx_product_seller ON products(seller_id)
     * idx_order_user ON orders(user_id)
     * idx_order_status ON orders(status)
     * idx_review_product ON reviews(product_id)
     */
    
    public static void main(String[] args) {
        System.out.println("=== Data Modeling Demo - E-Commerce System ===\n");
        
        // Create entities
        User customer = new User(1, "john@example.com", "John Doe", UserRole.CUSTOMER);
        User seller = new User(2, "seller@store.com", "Store Inc", UserRole.SELLER);
        
        Category electronics = new Category(1, "Electronics");
        Category phones = new Category(2, "Phones");
        phones.parentCategoryId = electronics.getCategoryId();
        
        Product iPhone = new Product(101, "iPhone 15 Pro", 999.99);
        iPhone.setSellerId(seller.getUserId());
        iPhone.setCategoryId(phones.getCategoryId());
        iPhone.setStockQuantity(50);
        
        Order order = new Order(1001, customer.getUserId());
        OrderItem item = new OrderItem(1, order.getOrderId(), iPhone.getProductId(), 2, iPhone.getPrice());
        order.addItem(item);
        
        // Display schema
        System.out.println("📊 Entities Created:");
        System.out.println("  • User: " + customer);
        System.out.println("  • Seller: " + seller);
        System.out.println("  • Category: " + electronics.getName());
        System.out.println("  • Sub-category: " + phones.getName());
        System.out.println("  • Product: " + iPhone.getName() + " - $" + iPhone.getPrice());
        System.out.println("  • Order #" + order.getOrderId() + " - Total: $" + order.getTotalAmount());
        
        System.out.println("\n📋 Database Schema:");
        System.out.println("  • Users, Addresses, Categories, Products");
        System.out.println("  • ProductVariants, Orders, OrderItems");
        System.out.println("  • Payments, Shippings, Reviews");
        
        System.out.println("\n🔗 Relationships Defined:");
        System.out.println("  • User → Addresses, Orders, Reviews, Products (seller)");
        System.out.println("  • Category → Subcategories (self-ref), Products");
        System.out.println("  • Product → Variants, Reviews, OrderItems");
        System.out.println("  • Order → OrderItems, Payment, Shipping");
        
        System.out.println("\n✅ Data modeling complete - normalized to 3NF!");
    }
}
