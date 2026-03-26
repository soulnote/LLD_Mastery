/**
 * EXAMPLE: System Design Process - Library Management System
 * 
 * This example demonstrates the system design process
 * for a Library Management System
 */

public class DesignProcess {
    
    // ============================================
    // STEP 1: REQUIREMENTS GATHERING
    // ============================================
    
    /*
     * Functional Requirements:
     * 1. Users can borrow and return books
     * 2. Users can search for books
     * 3. Librarians can add/remove books
     * 4. System tracks due dates and fines
     * 5. Users can reserve books
     * 
     * Non-Functional Requirements:
     * 1. System should handle 1000+ concurrent users
     * 2. Response time < 2 seconds
     * 3. Data should be encrypted
     * 4. System should be available 24/7
     */
    
    // ============================================
    // STEP 2: ACTOR IDENTIFICATION
    // ============================================
    
    /*
     * Actors:
     * 1. Member - Borrows books, returns books, searches catalog
     * 2. Librarian - Manages books, issues cards, handles fines
     * 3. Admin - System configuration, reports
     * 4. System - Automated tasks (due date reminders)
     */
    
    // ============================================
    // STEP 3: USE CASE ANALYSIS
    // ============================================
    
    /*
     * Use Cases:
     * 
     * UC1: Search Book
     * - Actor: Member
     * - Flow: Member enters search criteria → System displays results
     * 
     * UC2: Borrow Book
     * - Actor: Member
     * - Flow: Member requests book → System checks availability → 
     *         System issues book → Member receives book
     * 
     * UC3: Return Book
     * - Actor: Member
     * - Flow: Member returns book → System checks due date → 
     *         System calculates fine if late → Update records
     * 
     * UC4: Add Book
     * - Actor: Librarian
     * - Flow: Librarian enters book details → System validates → 
     *         System adds to catalog
     * 
     * UC5: Reserve Book
     * - Actor: Member
     * - Flow: Member requests reservation → System checks availability → 
     *         System adds to waitlist
     */
    
    // ============================================
    // STEP 4: ENTITY IDENTIFICATION
    // ============================================
    
    /*
     * Entities:
     * 
     * Book:
     * - id: String
     * - title: String
     * - author: String
     * - isbn: String
     * - copies: int
     * - availableCopies: int
     * 
     * Member:
     * - id: String
     * - name: String
     * - email: String
     * - membershipDate: Date
     * - status: MembershipStatus
     * 
     * Loan (Borrowing Record):
     * - id: String
     * - bookId: String
     * - memberId: String
     * - borrowDate: Date
     * - dueDate: Date
     * - returnDate: Date
     * - fine: double
     * 
     * Reservation:
     * - id: String
     * - bookId: String
     * - memberId: String
     * - reservationDate: Date
     * - status: ReservationStatus
     */
    
    // ============================================
    // STEP 5: RELATIONSHIPS
    // ============================================
    
    /*
     * Relationships:
     * 
     * Member 1----* Loan (One member can have many loans)
     * Book 1----* Loan (One book can have many loans)
     * Member 1----* Reservation (One member can have many reservations)
     * Book 1----* Reservation (One book can have many reservations)
     */
    
    public static void main(String[] args) {
        System.out.println("📋 System Design Process - Library Management System\n");
        
        System.out.println("=== Step 1: Requirements ===");
        System.out.println("Functional: Search, Borrow, Return, Add books, Reserve");
        System.out.println("Non-Functional: Performance, Security, Availability");
        
        System.out.println("\n=== Step 2: Actors ===");
        System.out.println("- Member: Borrows/returns/searches books");
        System.out.println("- Librarian: Manages books and members");
        System.out.println("- Admin: System configuration");
        
        System.out.println("\n=== Step 3: Use Cases ===");
        System.out.println("- Search Book, Borrow Book, Return Book");
        System.out.println("- Add Book, Reserve Book");
        
        System.out.println("\n=== Step 4: Entities ===");
        System.out.println("- Book, Member, Loan, Reservation");
        
        System.out.println("\n=== Step 5: Relationships ===");
        System.out.println("- Member 1----* Loan");
        System.out.println("- Book 1----* Loan");
        
        System.out.println("\n✅ Design complete! Ready for implementation.");
    }
}
