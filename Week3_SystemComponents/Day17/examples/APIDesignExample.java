/**
 * EXAMPLE: REST API Design - E-Commerce API
 */

public class APIDesignExample {
    
    /*
     * REST API DESIGN - E-Commerce Platform
     * =====================================
     * 
     * BASE URL: /api/v1
     * 
     * RESOURCES:
     * ----------
     * Users, Products, Categories, Orders, Reviews
     * 
     * HTTP METHODS:
     * -------------
     * GET    - Read/Retrieve
     * POST   - Create
     * PUT    - Full Update
     * PATCH  - Partial Update
     * DELETE - Delete
     * 
     * API ENDPOINTS:
     * --------------
     * 
     * USERS:
     * GET    /users              - List all users
     * GET    /users/{id}        - Get user by ID
     * POST   /users              - Create new user
     * PUT    /users/{id}         - Update user
     * DELETE /users/{id}         - Delete user
     * 
     * PRODUCTS:
     * GET    /products                      - List products (paginated)
     * GET    /products/{id}                 - Get product details
     * GET    /products?category={cat}      - Filter by category
     * GET    /products?search={term}       - Search products
     * POST   /products                      - Create product (seller only)
     * PUT    /products/{id}                 - Update product
     * DELETE /products/{id}                 - Delete product
     * 
     * ORDERS:
     * GET    /orders            - List user's orders
     * GET    /orders/{id}       - Get order details
     * POST   /orders            - Create new order
     * PATCH  /orders/{id}/cancel - Cancel order
     * 
     * STATUS CODES:
     * -------------
     * 200 OK - Successful GET/PUT/PATCH
     * 201 Created - Successful POST
     * 204 No Content - Successful DELETE
     * 400 Bad Request - Validation error
     * 401 Unauthorized - Not authenticated
     * 403 Forbidden - Not authorized
     * 404 Not Found - Resource doesn't exist
     * 500 Internal Server Error
     */
    
    // Example request/response models
    static class CreateUserRequest {
        String name;
        String email;
        String password;
    }
    
    static class UserResponse {
        int id;
        String name;
        String email;
        String createdAt;
    }
    
    static class ProductResponse {
        int id;
        String name;
        double price;
        String category;
        int stock;
    }
    
    public static void main(String[] args) {
        System.out.println("=== REST API Design Example ===");
        System.out.println("\n📡 API Endpoints designed for E-Commerce");
        System.out.println("✅ REST principles applied!");
    }
}
