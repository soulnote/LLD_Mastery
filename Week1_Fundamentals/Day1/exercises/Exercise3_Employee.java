/**
 * EXERCISE 3: Employee Management System
 * 
 * TASK: Design an Employee hierarchy combining Encapsulation and Inheritance
 * 
 * Requirements:
 * 1. Create Employee base class with: id, name, baseSalary (encapsulated)
 * 2. Create FullTimeEmployee: has benefits, overtime calculation
 * 3. Create PartTimeEmployee: hourly rate, hours worked
 * 4. Create Contractor: project-based payment, contract duration
 * 5. Each should calculate total compensation differently
 * 6. Add employee validation and proper access control
 * 
 * DIFFICULTY: ⭐⭐⭐⭐☆
 * TIME: 40 minutes
 */

public class Exercise3_Employee {
    
    // TODO: Implement Employee base class
    static abstract class Employee {
        // TODO: Add private fields: id, name, baseSalary
        
        // TODO: Add constructor with validation
        
        // TODO: Add abstract method: calculateTotalCompensation()
        
        // TODO: Add getters and appropriate setters
        
        // TODO: Add displayEmployeeInfo() method
    }
    
    // TODO: Implement FullTimeEmployee
    static class FullTimeEmployee extends Employee {
        // TODO: Add benefits amount and overtime hours
        
        // TODO: Implement constructor
        
        // TODO: Implement calculateTotalCompensation()
        // Formula: baseSalary + benefits + (overtimeHours * hourlyRate * 1.5)
        
        // TODO: Add specific methods for full-time employees
    }
    
    // TODO: Implement PartTimeEmployee
    static class PartTimeEmployee extends Employee {
        // TODO: Add hourly rate and hours worked
        
        // TODO: Implement constructor
        
        // TODO: Implement calculateTotalCompensation()
        // Formula: hourlyRate * hoursWorked
        
        // TODO: Add validation for maximum hours per week
    }
    
    // TODO: Implement Contractor
    static class Contractor extends Employee {
        // TODO: Add project payment and contract duration
        
        // TODO: Implement constructor
        
        // TODO: Implement calculateTotalCompensation()
        // Formula: projectPayment (fixed amount)
        
        // TODO: Add contract management methods
    }
    
    // Test your implementation
    public static void main(String[] args) {
        System.out.println("👥 Exercise 3: Employee Management System");
        
        // TODO: Create different types of employees and test
        // Test cases:
        // 1. Create FullTimeEmployee with overtime
        // 2. Create PartTimeEmployee with 25 hours/week
        // 3. Create Contractor with project payment
        // 4. Calculate and display total compensation for each
        // 5. Test validation (negative salary, invalid hours, etc.)
        
        System.out.println("✅ Complete the implementation and run tests!");
    }
}