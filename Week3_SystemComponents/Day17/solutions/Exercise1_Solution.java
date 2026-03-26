/**
 * SOLUTION: Social Media API Design
 */

public class Exercise1_Solution {
    
    /*
     * SOCIAL MEDIA API DESIGN - Complete Solution
     * ===========================================
     * 
     * BASE URL: /api/v1
     * AUTH: JWT Bearer Token
     * 
     * ========================================
     * USERS
     * ========================================
     * POST   /auth/register          - Register new user
     * POST   /auth/login            - Login (returns JWT)
     * GET    /users/me              - Get current user profile
     * GET    /users/{username}     - Get user profile by username
     * PUT    /users/me              - Update current user profile
     * GET    /users/{id}/followers - Get user's followers
     * GET    /users/{id}/following - Get users being followed
     * 
     * ========================================
     * POSTS
     * ========================================
     * GET    /posts                 - Get feed (paginated)
     * GET    /posts/{id}            - Get post by ID
     * POST   /posts                 - Create new post
     * PUT    /posts/{id}            - Update post (owner only)
     * DELETE /posts/{id}           - Delete post (owner only)
     * GET    /users/{id}/posts     - Get user's posts
     * 
     * ========================================
     * COMMENTS
     * ========================================
     * GET    /posts/{id}/comments   - Get comments for post
     * POST   /posts/{id}/comments   - Add comment
     * PUT    /comments/{id}         - Update comment (owner only)
     * DELETE /comments/{id}        - Delete comment (owner only)
     * 
     * ========================================
     * LIKES/REACTIONS
     * ========================================
     * POST   /posts/{id}/like       - Like a post
     * DELETE /posts/{id}/like       - Unlike a post
     * POST   /comments/{id}/like    - Like a comment
     * 
     * ========================================
     * FOLLOWS
     * ========================================
     * POST   /users/{id}/follow     - Follow a user
     * DELETE /users/{id}/follow    - Unfollow a user
     * 
     * ========================================
     * PAGINATION & FILTERING
     * ========================================
     * GET /posts?page=0&size=20&sort=createdAt,desc
     * GET /posts?userId=123&fromDate=2024-01-01
     * 
     * ========================================
     * ERROR RESPONSES
     * ========================================
     * 400 - Validation Error
     * 401 - Unauthorized
     * 403 - Forbidden
     * 404 - Not Found
     * 429 - Too Many Requests (Rate Limit)
     */
    
    public static void main(String[] args) {
        System.out.println("📝 Social Media API - SOLUTION\n");
        
        System.out.println("╔══════════════════════════════════════════════════════════════════╗");
        System.out.println("║             SOCIAL MEDIA REST API DESIGN                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("👤 AUTHENTICATION:");
        System.out.println("------------------");
        System.out.println("POST /auth/register → 201 Created");
        System.out.println("POST /auth/login    → 200 OK (JWT token)");
        
        System.out.println("\n👥 USERS:");
        System.out.println("----------");
        System.out.println("GET  /users/me           → Current user profile");
        System.out.println("GET  /users/{username}  → User by username");
        System.out.println("PUT  /users/me          → Update profile");
        
        System.out.println("\n📝 POSTS:");
        System.out.println("--------");
        System.out.println("GET    /posts           → Feed (paginated)");
        System.out.println("GET    /posts/{id}      → Single post");
        System.out.println("POST   /posts           → Create post");
        System.out.println("PUT    /posts/{id}      → Update post");
        System.out.println("DELETE /posts/{id}     → Delete post");
        
        System.out.println("\n💬 COMMENTS:");
        System.out.println("-----------");
        System.out.println("GET    /posts/{id}/comments → List comments");
        System.out.println("POST   /posts/{id}/comments → Add comment");
        
        System.out.println("👍 LIKES:");
        System.out.println("--------");
        System.out.println("POST   /posts/{id}/like → Like");
        System.out.println("DELETE /posts/{id}/like → Unlike");
        
        System.out.println("\n👥 FOLLOWS:");
        System.out.println("----------");
        System.out.println("POST   /users/{id}/follow → Follow");
        System.out.println("DELETE /users/{id}/follow → Unfollow");
        
        System.out.println("\n✅ API design complete!");
    }
}
