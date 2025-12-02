/**
 * SOLUTION: Exercise 1 - Library Management System
 * Demonstrates proper encapsulation with validation and controlled access
 */

public class Exercise1_Solution {
    
    static class Book {
        private String isbn;
        private String title;
        private String author;
        private boolean isAvailable;
        
        public Book(String isbn, String title, String author) {
            if (!isValidISBN(isbn)) {
                throw new IllegalArgumentException("Invalid ISBN format. Must be 13 digits.");
            }
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.isAvailable = true;
        }
        
        private boolean isValidISBN(String isbn) {
            return isbn != null && isbn.matches("\\d{13}");
        }
        
        public boolean borrowBook() {
            if (isAvailable) {
                isAvailable = false;
                System.out.println("✅ Book '" + title + "' borrowed successfully!");
                return true;
            } else {
                System.out.println("❌ Book '" + title + "' is already borrowed!");
                return false;
            }
        }
        
        public boolean returnBook() {
            if (!isAvailable) {
                isAvailable = true;
                System.out.println("✅ Book '" + title + "' returned successfully!");
                return true;
            } else {
                System.out.println("❌ Book '" + title + "' was not borrowed!");
                return false;
            }
        }
        
        public String getBookInfo() {
            return String.format("📖 %s by %s (ISBN: %s) - %s", 
                title, author, isbn, isAvailable ? "Available" : "Borrowed");
        }
        
        // Getters
        public String getIsbn() { return isbn; }
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public boolean isAvailable() { return isAvailable; }
        
        // Controlled setters
        public void setTitle(String title) {
            if (title != null && !title.trim().isEmpty()) {
                this.title = title;
            }
        }
        
        public void setAuthor(String author) {
            if (author != null && !author.trim().isEmpty()) {
                this.author = author;
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("📚 Solution: Library Management System\n");
        
        try {
            // Test valid book creation
            Book book1 = new Book("9781234567890", "Clean Code", "Robert Martin");
            System.out.println(book1.getBookInfo());
            
            // Test borrowing
            book1.borrowBook();
            System.out.println(book1.getBookInfo());
            
            // Try to borrow again
            book1.borrowBook();
            
            // Return book
            book1.returnBook();
            System.out.println(book1.getBookInfo());
            
            // Try to return again
            book1.returnBook();
            
            System.out.println();
            
            // Test invalid ISBN
            try {
                Book invalidBook = new Book("123", "Invalid Book", "Unknown");
            } catch (IllegalArgumentException e) {
                System.out.println("❌ " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n🎯 Key Learning: Encapsulation protects object state and ensures data integrity!");
    }
}