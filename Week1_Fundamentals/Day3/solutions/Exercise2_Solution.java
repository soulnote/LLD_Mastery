/**
 * SOLUTION: Report Generation System
 * Demonstrates Single Responsibility Principle (SRP)
 */

// RESPONSIBILITY 1: Holds report data only
class Report {
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

// RESPONSIBILITY 2: Collects data only
class DataCollector {
    public Report collectData(String reportType) {
        System.out.println("📊 [DataCollector] Gathering data for: " + reportType);
        
        String content = "";
        
        switch (reportType.toLowerCase()) {
            case "sales":
                content = collectSalesData();
                break;
            case "users":
                content = collectUserData();
                break;
            case "inventory":
                content = collectInventoryData();
                break;
            default:
                content = "Unknown report type";
        }
        
        return new Report(reportType + " Report", content);
    }
    
    private String collectSalesData() {
        System.out.println("   Fetching sales from database...");
        return "Total Sales: $50,000\n" +
               "Products Sold: 1,234\n" +
               "Average Order: $40.50";
    }
    
    private String collectUserData() {
        System.out.println("   Fetching user statistics...");
        return "Total Users: 5,000\n" +
               "Active Users: 3,500\n" +
               "New Users Today: 50";
    }
    
    private String collectInventoryData() {
        System.out.println("   Fetching inventory data...");
        return "Total Products: 500\n" +
               "Low Stock Items: 12\n" +
               "Out of Stock: 3";
    }
}

// RESPONSIBILITY 3: Formats reports only
class ReportFormatter {
    public String format(Report report, String formatType) {
        System.out.println("📝 [ReportFormatter] Formatting as: " + formatType);
        
        report.setFormat(formatType);
        
        switch (formatType.toLowerCase()) {
            case "html":
                return formatAsHtml(report);
            case "pdf":
                return formatAsPdf(report);
            case "csv":
                return formatAsCsv(report);
            case "json":
                return formatAsJson(report);
            default:
                return report.getContent();
        }
    }
    
    private String formatAsHtml(Report report) {
        return "<html><body>" +
               "<h1>" + report.getTitle() + "</h1>" +
               "<p>" + report.getContent() + "</p>" +
               "<small>Generated: " + report.getGeneratedDate() + "</small>" +
               "</body></html>";
    }
    
    private String formatAsPdf(Report report) {
        return "[PDF FORMAT]\n" +
               "=============\n" +
               report.getTitle() + "\n" +
               "=============\n" +
               report.getContent() + "\n" +
               "Generated: " + report.getGeneratedDate();
    }
    
    private String formatAsCsv(Report report) {
        return "Title,Content,Date\n" +
               "\"" + report.getTitle() + "\",\"" + 
               report.getContent().replace("\n", ";") + "\"," +
               report.getGeneratedDate();
    }
    
    private String formatAsJson(Report report) {
        return "{\n" +
               "  \"title\": \"" + report.getTitle() + "\",\n" +
               "  \"content\": \"" + report.getContent().replace("\n", "\\n") + "\",\n" +
               "  \"date\": \"" + report.getGeneratedDate() + "\"\n" +
               "}";
    }
}

// RESPONSIBILITY 4: Delivers reports only
class ReportDelivery {
    public void deliver(String formattedReport, String destination) {
        System.out.println("📤 [ReportDelivery] Delivering to: " + destination);
        
        switch (destination.toLowerCase()) {
            case "file":
                saveToFile(formattedReport);
                break;
            case "email":
                sendViaEmail(formattedReport);
                break;
            case "cloud":
                uploadToCloud(formattedReport);
                break;
            default:
                System.out.println("   Unknown destination: " + destination);
        }
    }
    
    private void saveToFile(String report) {
        System.out.println("   💾 Saving to: reports/report_" + 
            System.currentTimeMillis() + ".txt");
        System.out.println("   ✅ File saved!");
    }
    
    private void sendViaEmail(String report) {
        System.out.println("   📧 Sending email to: manager@company.com");
        System.out.println("   Subject: Report Available");
        System.out.println("   ✅ Email sent!");
    }
    
    private void uploadToCloud(String report) {
        System.out.println("   ☁️ Uploading to: AWS S3 / reports bucket");
        System.out.println("   ✅ Upload complete!");
    }
}

// COORDINATOR: Orchestrates the report generation process
class ReportGenerator {
    private DataCollector dataCollector;
    private ReportFormatter formatter;
    private ReportDelivery delivery;
    
    public ReportGenerator() {
        this.dataCollector = new DataCollector();
        this.formatter = new ReportFormatter();
        this.delivery = new ReportDelivery();
    }
    
    public void generateReport(String reportType, String format, String destination) {
        System.out.println("\n🎯 Generating " + reportType + " report...\n");
        
        // Step 1: Collect data
        Report report = dataCollector.collectData(reportType);
        
        // Step 2: Format report
        String formattedReport = formatter.format(report, format);
        
        // Step 3: Deliver report
        delivery.deliver(formattedReport, destination);
        
        System.out.println("✅ Report generation complete!");
    }
}

public class Exercise2_Solution {
    public static void main(String[] args) {
        System.out.println("📊 Report Generation System - SOLUTION\n");
        
        // Create the report generator coordinator
        ReportGenerator generator = new ReportGenerator();
        
        // Generate different types of reports
        System.out.println("=== Sales Report (HTML) ===");
        generator.generateReport("sales", "html", "email");
        
        System.out.println("\n=== User Report (JSON) ===");
        generator.generateReport("users", "json", "file");
        
        System.out.println("\n=== Inventory Report (CSV) ===");
        generator.generateReport("inventory", "csv", "cloud");
        
        System.out.println("\n=== Benefits of SRP ===");
        System.out.println("✅ DataCollector: Can change data sources independently");
        System.out.println("✅ ReportFormatter: Can add new formats without touching other classes");
        System.out.println("✅ ReportDelivery: Can add new delivery methods easily");
        System.out.println("✅ ReportGenerator: Focuses on orchestration only");
        System.out.println("✅ Each component is testable in isolation");
        
        System.out.println("\n✅ Report Generation System complete!");
    }
}
