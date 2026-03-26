/**
 * EXAMPLE: UML Class Diagram Basics
 * 
 * This example shows how to interpret and create UML class diagrams
 * 
 * UML NOTATION USED:
 * ==================
 * 
 * 1. CLASS REPRESENTATION:
 * +------------------+
 * | ClassName        |  <- Class Name (bold, centered)
 * +------------------+
 * | - field: Type    |  <- Attributes (visibility: - private)
 * +------------------+
 * | + method()      |  <- Methods (visibility: + public)
 * +------------------+
 * 
 * 2. RELATIONSHIPS:
 * 
 * Inheritance (extends):    A △→ B  or  A ──△> B
 *                            (A extends B)
 * 
 * Interface:                <<interface>>
 *                            ────────
 *                            |A      |
 *                            ────────
 * 
 * Aggregation (has-a):      A ◇── B  (A has B, B can exist without A)
 * 
 * Composition (owns-a):     A ◆── B  (A owns B, B cannot exist without A)
 * 
 * Association:              A ───→ B  (A uses B)
 * 
 * Dependency:               A - - → B  (A depends on B temporarily)
 */

public class UMLBasics {
    
    // ============================================
    // EXAMPLE 1: Simple Class (Rectangle)
    // ============================================
    /*
     * UML DIAGRAM:
     * +------------------+
     * | Rectangle        |
     * +------------------+
     * | - width: double  |
     * | - height: double |
     * +------------------+
     * | + area(): double |
     * | + setWidth()     |
     * | + setHeight()    |
     * +------------------+
     */
    static class Rectangle {
        private double width;
        private double height;
        
        public double area() { return width * height; }
        public void setWidth(double w) { this.width = w; }
        public void setHeight(double h) { this.height = h; }
    }
    
    // ============================================
    // EXAMPLE 2: Inheritance (Square extends Rectangle)
    // ============================================
    /*
     * UML DIAGRAM:
     * 
     *     Rectangle
     *         △
     *         │
     *     Square
     * 
     * OR:
     * 
     *     ┌───────────┐
     *     │ Rectangle │
     *     └─────┬─────┘
     *           △
     *           │
     *     ┌─────┴─────┐
     *     │  Square  │
     *     └───────────┘
     */
    static class Square extends Rectangle {
        public void setSide(double side) {
            setWidth(side);
            setHeight(side);
        }
    }
    
    // ============================================
    // EXAMPLE 3: Interface (Shape)
    // ============================================
    /*
     * UML DIAGRAM:
     * 
     *     <<interface>>
     *     ┌───────────┐
     *     │  Shape   │
     *     └─────┬─────┘
     *           △
     *           │
     *     ┌─────┴─────┐
     *     │ Rectangle │
     *     └───────────┘
     */
    interface Shape {
        double area();
    }
    
    // ============================================
    // EXAMPLE 4: Aggregation (Library has Books)
    // ============================================
    /*
     * UML DIAGRAM:
     * 
     *     ┌─────────┐       ┌──────┐
     *     │ Library │ ◇──── │ Book │
     *     └─────────┘  1..* └──────┘
     *     
     *     (1 Library has many Books, Books can exist without Library)
     */
    
    // ============================================
    // EXAMPLE 5: Composition (Order has OrderItems)
    // ============================================
    /*
     * UML DIAGRAM:
     * 
     *     ┌──────┐       ┌────────────┐
     *     │Order │ ◆──── │ OrderItem  │
     *     └──────┘  1..* └────────────┘
     *     
     *     (Order owns OrderItems, Items cannot exist without Order)
     */
    
    public static void main(String[] args) {
        System.out.println("📊 UML Class Diagram Basics\n");
        
        System.out.println("=== 1. Class Notation ===");
        System.out.println("+------------------+");
        System.out.println("| ClassName        |");
        System.out.println("+------------------+");
        System.out.println("| - field: Type    |  (-) private");
        System.out.println("| # field: Type    |  (#) protected");
        System.out.println("| + method()       |  (+) public");
        System.out.println("+------------------+");
        
        System.out.println("\n=== 2. Relationships ===");
        System.out.println("△ Inheritance:     A extends B");
        System.out.println("◇ Aggregation:    A has B (B can exist without A)");
        System.out.println("◆ Composition:   A owns B (B cannot exist without A)");
        System.out.println("─── Association: A uses B");
        System.out.println("- - Dependency:   A temporarily uses B");
        
        System.out.println("\n=== 3. Multiplicity ===");
        System.out.println("1         : Exactly one");
        System.out.println("0..1      : Optional (zero or one)");
        System.out.println("1..*      : One or more");
        System.out.println("0..*      : Zero or more (many)");
        System.out.println("n..m      : Between n and m");
        
        System.out.println("\n✅ Understanding UML basics!");
    }
}
