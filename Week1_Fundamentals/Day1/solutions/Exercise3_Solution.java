/**
 * SOLUTION: Exercise 3 - Employee Management System
 * Demonstrates both encapsulation and inheritance working together
 */

public class Exercise3_Solution {
    
    static abstract class Employee {
        private int id;
        private String name;
        private double baseSalary;
        
        public Employee(int id, String name, double baseSalary) {
            if (id <= 0) {
                throw new IllegalArgumentException("Employee ID must be positive");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Employee name cannot be empty");
            }
            if (baseSalary < 0) {
                throw new IllegalArgumentException("Base salary cannot be negative");
            }
            
            this.id = id;
            this.name = name.trim();
            this.baseSalary = baseSalary;
        }
        
        public abstract double calculateTotalCompensation();
        
        public void displayEmployeeInfo() {
            System.out.printf("👤 %s (ID: %d) - %s%n", name, id, this.getClass().getSimpleName());
            System.out.printf("   Base Salary: $%.2f%n", baseSalary);
            System.out.printf("   Total Compensation: $%.2f%n", calculateTotalCompensation());
        }
        
        // Getters
        public int getId() { return id; }
        public String getName() { return name; }
        public double getBaseSalary() { return baseSalary; }
        
        // Controlled setters
        public void setName(String name) {
            if (name != null && !name.trim().isEmpty()) {
                this.name = name.trim();
            }
        }
        
        public void setBaseSalary(double baseSalary) {
            if (baseSalary >= 0) {
                this.baseSalary = baseSalary;
            }
        }
    }
    
    static class FullTimeEmployee extends Employee {
        private double benefits;
        private double overtimeHours;
        private static final double HOURLY_RATE = 25.0; // Assuming $25/hour
        private static final double OVERTIME_MULTIPLIER = 1.5;
        
        public FullTimeEmployee(int id, String name, double baseSalary, double benefits, double overtimeHours) {
            super(id, name, baseSalary);
            if (benefits < 0) {
                throw new IllegalArgumentException("Benefits cannot be negative");
            }
            if (overtimeHours < 0) {
                throw new IllegalArgumentException("Overtime hours cannot be negative");
            }
            
            this.benefits = benefits;
            this.overtimeHours = overtimeHours;
        }
        
        @Override
        public double calculateTotalCompensation() {
            double overtimePay = overtimeHours * HOURLY_RATE * OVERTIME_MULTIPLIER;
            return getBaseSalary() + benefits + overtimePay;
        }
        
        @Override
        public void displayEmployeeInfo() {
            super.displayEmployeeInfo();
            System.out.printf("   Benefits: $%.2f%n", benefits);
            System.out.printf("   Overtime Hours: %.1f (Pay: $%.2f)%n", 
                overtimeHours, overtimeHours * HOURLY_RATE * OVERTIME_MULTIPLIER);
        }
        
        public double getBenefits() { return benefits; }
        public double getOvertimeHours() { return overtimeHours; }
        
        public void setBenefits(double benefits) {
            if (benefits >= 0) this.benefits = benefits;
        }
        
        public void setOvertimeHours(double overtimeHours) {
            if (overtimeHours >= 0) this.overtimeHours = overtimeHours;
        }
    }
    
    static class PartTimeEmployee extends Employee {
        private double hourlyRate;
        private double hoursWorked;
        private static final double MAX_HOURS_PER_WEEK = 40.0;
        
        public PartTimeEmployee(int id, String name, double baseSalary, double hourlyRate, double hoursWorked) {
            super(id, name, baseSalary);
            if (hourlyRate <= 0) {
                throw new IllegalArgumentException("Hourly rate must be positive");
            }
            if (hoursWorked < 0 || hoursWorked > MAX_HOURS_PER_WEEK) {
                throw new IllegalArgumentException("Hours worked must be between 0 and " + MAX_HOURS_PER_WEEK);
            }
            
            this.hourlyRate = hourlyRate;
            this.hoursWorked = hoursWorked;
        }
        
        @Override
        public double calculateTotalCompensation() {
            return hourlyRate * hoursWorked;
        }
        
        @Override
        public void displayEmployeeInfo() {
            super.displayEmployeeInfo();
            System.out.printf("   Hourly Rate: $%.2f%n", hourlyRate);
            System.out.printf("   Hours Worked: %.1f%n", hoursWorked);
        }
        
        public double getHourlyRate() { return hourlyRate; }
        public double getHoursWorked() { return hoursWorked; }
        
        public void setHourlyRate(double hourlyRate) {
            if (hourlyRate > 0) this.hourlyRate = hourlyRate;
        }
        
        public void setHoursWorked(double hoursWorked) {
            if (hoursWorked >= 0 && hoursWorked <= MAX_HOURS_PER_WEEK) {
                this.hoursWorked = hoursWorked;
            }
        }
    }
    
    static class Contractor extends Employee {
        private double projectPayment;
        private int contractDurationMonths;
        
        public Contractor(int id, String name, double baseSalary, double projectPayment, int contractDurationMonths) {
            super(id, name, baseSalary);
            if (projectPayment <= 0) {
                throw new IllegalArgumentException("Project payment must be positive");
            }
            if (contractDurationMonths <= 0) {
                throw new IllegalArgumentException("Contract duration must be positive");
            }
            
            this.projectPayment = projectPayment;
            this.contractDurationMonths = contractDurationMonths;
        }
        
        @Override
        public double calculateTotalCompensation() {
            return projectPayment; // Fixed project-based payment
        }
        
        @Override
        public void displayEmployeeInfo() {
            super.displayEmployeeInfo();
            System.out.printf("   Project Payment: $%.2f%n", projectPayment);
            System.out.printf("   Contract Duration: %d months%n", contractDurationMonths);
        }
        
        public boolean isContractActive() {
            // Simplified - in real system would check dates
            return contractDurationMonths > 0;
        }
        
        public void extendContract(int additionalMonths) {
            if (additionalMonths > 0) {
                this.contractDurationMonths += additionalMonths;
                System.out.printf("✅ Contract extended by %d months%n", additionalMonths);
            }
        }
        
        public double getProjectPayment() { return projectPayment; }
        public int getContractDurationMonths() { return contractDurationMonths; }
    }
    
    public static void main(String[] args) {
        System.out.println("👥 Solution: Employee Management System\n");
        
        try {
            // Create different types of employees
            FullTimeEmployee fullTime = new FullTimeEmployee(1, "Alice Johnson", 80000, 15000, 10);
            PartTimeEmployee partTime = new PartTimeEmployee(2, "Bob Smith", 0, 20, 25);
            Contractor contractor = new Contractor(3, "Carol Davis", 0, 50000, 6);
            
            // Display employee information
            System.out.println("📋 Employee Details:");
            fullTime.displayEmployeeInfo();
            System.out.println();
            
            partTime.displayEmployeeInfo();
            System.out.println();
            
            contractor.displayEmployeeInfo();
            System.out.println();
            
            // Test polymorphism
            Employee[] employees = {fullTime, partTime, contractor};
            System.out.println("💰 Total Compensation Summary:");
            double totalPayroll = 0;
            for (Employee emp : employees) {
                double compensation = emp.calculateTotalCompensation();
                System.out.printf("%s: $%.2f%n", emp.getName(), compensation);
                totalPayroll += compensation;
            }
            System.out.printf("Total Payroll: $%.2f%n", totalPayroll);
            
            System.out.println();
            
            // Test validation
            try {
                PartTimeEmployee invalidEmployee = new PartTimeEmployee(4, "Invalid", 0, 15, 50); // Too many hours
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Validation Error: " + e.getMessage());
            }
            
            // Test contract extension
            System.out.println("\n🔄 Contract Management:");
            contractor.extendContract(3);
            System.out.printf("New contract duration: %d months%n", contractor.getContractDurationMonths());
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n🎯 Key Learning: Combining encapsulation and inheritance creates robust, maintainable systems!");
    }
}