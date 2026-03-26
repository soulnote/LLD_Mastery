/**
 * SOLUTION: Library System Design
 * Complete design document for Library Management System
 */

public class Exercise1_Solution {
    
    // ACTORS: Who interacts with the system?
    static String[] actors = {
        "Member - Borrows and returns books, searches catalog, reserves books",
        "Librarian - Manages books, issues membership, handles fines",
        "Administrator - System configuration, reports, user management",
        "System - Automated tasks (due date reminders, late fee calculation)"
    };
    
    // FUNCTIONAL REQUIREMENTS: What should the system do?
    static String[] functionalRequirements = {
        "FR1: Users can search for books by title, author, or ISBN",
        "FR2: Members can borrow books (subject to availability)",
        "FR3: Members can return books (system calculates fines if late)",
        "FR4: Librarians can add, update, and remove books from catalog",
        "FR5: Members can reserve books that are currently checked out",
        "FR6: System sends reminders for upcoming due dates",
        "FR7: System tracks membership and issues/renews library cards",
        "FR8: System generates reports (popular books, overdue items, etc.)"
    };
    
    // NON-FUNCTIONAL REQUIREMENTS: Quality attributes
    static String[] nonFunctionalRequirements = {
        "NFR1: Performance - Response time < 2 seconds for search queries",
        "NFR2: Scalability - Support 1000+ concurrent users",
        "NFR3: Security - User authentication and data encryption",
        "NFR4: Availability - System should be available 24/7",
        "NFR5: Maintainability - Code should follow SOLID principles"
    };
    
    // ENTITIES: Main objects in the system
    static String[] entities = {
        "Book: id, title, author, isbn, copies, availableCopies, category",
        "Member: id, name, email, phone, membershipDate, status, fines",
        "Loan: id, bookId, memberId, borrowDate, dueDate, returnDate, fine",
        "Reservation: id, bookId, memberId, reservationDate, status",
        "Librarian: id, name, employeeId, accessLevel"
    };
    
    // USE CASES: Key interactions
    static String[] useCases = {
        "UC1: Search Book - Member searches catalog by various criteria",
        "UC2: Borrow Book - Member borrows available book",
        "UC3: Return Book - Member returns book, system calculates fine if late",
        "UC4: Reserve Book - Member reserves unavailable book for later",
        "UC5: Add Book - Librarian adds new book to catalog",
        "UC6: Remove Book - Librarian removes book from catalog",
        "UC7: Manage Member - Librarian creates/updates member records",
        "UC8: Pay Fine - Member pays outstanding fines",
        "UC9: Generate Report - Admin generates system reports"
    };
    
    // RELATIONSHIPS
    static String[] relationships = {
        "Member 1 ---- * Loan (One member can have many loans)",
        "Book 1 ---- * Loan (One book can have many loans)",
        "Member 1 ---- * Reservation (One member can have many reservations)",
        "Book 1 ---- * Reservation (One book can have many reservations)"
    };
    
    public static void main(String[] args) {
        System.out.println("📚 Library System Design - SOLUTION\n");
        
        System.out.println("=== Actors ===");
        for (String actor : actors) {
            System.out.println("- " + actor);
        }
        
        System.out.println("\n=== Functional Requirements ===");
        for (String req : functionalRequirements) {
            System.out.println("- " + req);
        }
        
        System.out.println("\n=== Non-Functional Requirements ===");
        for (String req : nonFunctionalRequirements) {
            System.out.println("- " + req);
        }
        
        System.out.println("\n=== Entities ===");
        for (String entity : entities) {
            System.out.println("- " + entity);
        }
        
        System.out.println("\n=== Use Cases ===");
        for (String useCase : useCases) {
            System.out.println("- " + useCase);
        }
        
        System.out.println("\n=== Relationships ===");
        for (String rel : relationships) {
            System.out.println("- " + rel);
        }
        
        System.out.println("\n✅ Design document complete!");
    }
}
