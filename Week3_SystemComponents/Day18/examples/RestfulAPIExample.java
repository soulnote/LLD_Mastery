/**
 * EXAMPLE: RESTful API Best Practices
 */

public class RestfulAPIExample {
    
    /*
     * RESTful API BEST PRACTICES
     * =========================
     * 
     * 1. RESOURCE NAMING
     * ------------------
     * - Use nouns, not verbs: /users not /getUsers
     * - Use plural: /users not /user
     * - Use kebab-case: /user-profiles
     * - Nest resources: /users/123/orders
     * 
     * 2. HTTP STATUS CODES
     * --------------------
     * 200 OK - Success
     * 201 Created - Resource created
     * 204 No Content - Success with no response body
     * 400 Bad Request - Client error
     * 401 Unauthorized - Authentication required
     * 403 Forbidden - No permission
     * 404 Not Found - Resource doesn't exist
     * 500 Server Error
     * 
     * 3. VERSIONING
     * -------------
     * - URL: /api/v1/users
     * - Header: Accept: application/vnd.app.v1+json
     * 
     * 4. PAGINATION
     * -------------
     * GET /users?page=0&size=20
     * Response: { content: [], page: 0, totalPages: 10 }
     * 
     * 5. ERROR HANDLING
     * ----------------
     * {
     *   "error": {
     *     "code": "USER_NOT_FOUND",
     *     "message": "User with ID 123 not found",
     *     "timestamp": "2024-01-15T10:30:00Z"
     *   }
     * }
     * 
     * 6. HATEOAS (optional)
     * ---------------------
     * {
     *   "id": 123,
     *   "name": "John",
     *   "links": {
     *     "self": "/users/123",
     *     "orders": "/users/123/orders"
     *   }
     * }
     */
    
    // Example response model
    static class ApiResponse<T> {
        T data;
        String message;
        long timestamp;
    }
    
    static class ErrorResponse {
        String code;
        String message;
        String details;
    }
    
    public static void main(String[] args) {
        System.out.println("=== RESTful API Best Practices ===");
        System.out.println("\n✅ Best practices for API design:");
        System.out.println("• Resource naming conventions");
        System.out.println("• Proper HTTP status codes");
        System.out.println("• API versioning strategies");
        System.out.println("• Pagination implementation");
        System.out.println("• Error handling standards");
    }
}
