/**
 * EXAMPLE: ER Diagram Design - Library Management System
 */

public class ERDiagramExample {
    
    /*
     * ER DIAGRAM FOR LIBRARY MANAGEMENT SYSTEM
     * =========================================
     * 
     * ENTITIES:
     * ---------
     * 1. Member(member_id, name, email, phone, address, membership_date, membership_type)
     * 2. Book(book_id, isbn, title, author, publisher, category_id, total_copies, available_copies)
     * 3. Category(category_id, name, parent_category_id)
     * 4. BookIssue(issue_id, member_id, book_id, issue_date, due_date, return_date, fine)
     * 5. Author(author_id, name, biography)
     * 6. BookAuthor(book_id, author_id) - Junction table
     * 
     * RELATIONSHIPS:
     * --------------
     * Member 1 ---- * BookIssue (borrows)
     * Book 1 ---- * BookIssue (issued)
     * Category 1 ---- * Book (categorized)
     * Category * ---- 1 Category (parent-child hierarchy)
     * Book * ---- * Author (many-to-many)
     * 
     * CARDINALITIES:
     * --------------
     * - Member to BookIssue: One-to-Many
     * - Book to BookIssue: One-to-Many  
     * - Category to Book: One-to-Many
     * - Category self-reference: One-to-Many
     * - Book to Author: Many-to-Many
     */
    
    // Entity classes
    static class Member {
        private int memberId;
        private String name;
        private String email;
        private String membershipType;
    }
    
    static class Book {
        private int bookId;
        private String isbn;
        private String title;
        private int availableCopies;
    }
    
    static class Category {
        private int categoryId;
        private String name;
        private Integer parentCategoryId; // Self-reference
    }
    
    static class BookIssue {
        private int issueId;
        private int memberId;
        private int bookId;
        private String issueDate;
        private String dueDate;
        private String returnDate;
        private double fine;
    }
    
    public static void main(String[] args) {
        System.out.println("=== ER Diagram: Library Management ===");
        System.out.println("\n📊 Entities: Member, Book, Category, BookIssue, Author");
        System.out.println("🔗 Relationships defined with cardinalities!");
        System.out.println("✅ ER modeling complete!");
    }
}
