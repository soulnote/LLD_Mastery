/**
 * EXERCISE 2: Report Generation System
 * 
 * TASK: Separate concerns in a report generation system
 * 
 * Requirements:
 * 1. Create a Report class to hold data
 * 2. Extract data gathering into a DataCollector
 * 3. Extract formatting into a ReportFormatter
 * 4. Extract delivery into a ReportDelivery
 * 5. Create a ReportGenerator that coordinates all components
 * 
 * DIFFICULTY: ⭐⭐⭐☆☆
 * TIME: 30 minutes
 */

public class Exercise2_ReportGenerator {
    
    // Simple report data holder
    static class Report {
        private String title;
        private String content;
        private String format;
        private String generatedDate;
        
        public Report(String title, String content) {
            this.title = title;
            this.content = content;
            this.generatedDate = java.time.LocalDate.now().toString();
        }
        
        // Getters
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public String getFormat() { return format; }
        public String getGeneratedDate() { return generatedDate; }
        
        public void setFormat(String format) { this.format = format; }
    }
    
    // TODO: Create DataCollector - gathers report data
    // Responsibilities:
    // - Fetch sales data
    // - Fetch user statistics
    // - Aggregate data
    
    // TODO: Create ReportFormatter - formats report for different outputs
    // Responsibilities:
    // - Format as HTML
    // - Format as PDF
    // - Format as CSV
    // - Format as JSON
    
    // TODO: Create ReportDelivery - delivers report to destinations
    // Responsibilities:
    // - Save to file
    // - Send via email
    // - Upload to cloud
    
    // TODO: Create ReportGenerator - coordinates all components
    
    public static void main(String[] args) {
        System.out.println("📊 Exercise 2: Report Generation System\n");
        
        // TODO: Test your solution
        // 1. Create DataCollector and gather data
        // 2. Create ReportFormatter and format the report
        // 3. Create ReportDelivery and deliver the report
        // 4. Use ReportGenerator to coordinate everything
        
        System.out.println("✅ Complete the implementation!");
    }
}
