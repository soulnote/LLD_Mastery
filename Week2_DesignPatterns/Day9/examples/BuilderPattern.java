/**
 * EXAMPLE: Builder Pattern
 */

public class BuilderPattern {
    
    // Product - User
    static class User {
        private String name;
        private int age;
        private String email;
        private String phone;
        private String address;
        
        // Private constructor - can only be created via Builder
        private User(Builder builder) {
            this.name = builder.name;
            this.age = builder.age;
            this.email = builder.email;
            this.phone = builder.phone;
            this.address = builder.address;
        }
        
        @Override
        public String toString() {
            return "User{name='" + name + "', age=" + age + 
                   ", email='" + email + "', phone='" + phone + 
                   "', address='" + address + "'}";
        }
    }
    
    // Builder
    static class Builder {
        private String name;
        private int age;
        private String email;
        private String phone;
        private String address;
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public Builder address(String address) {
            this.address = address;
            return this;
        }
        
        public User build() {
            return new User(this);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Builder Pattern Demo ===\n");
        
        // Create user with builder - fluent interface
        User user1 = new Builder()
            .name("John")
            .age(30)
            .email("john@example.com")
            .build();
        
        User user2 = new Builder()
            .name("Jane")
            .age(25)
            .email("jane@example.com")
            .phone("1234567890")
            .address("123 Main St")
            .build();
        
        System.out.println("User1: " + user1);
        System.out.println("User2: " + user2);
        
        System.out.println("\n✅ Builder pattern demonstrated!");
    }
}
