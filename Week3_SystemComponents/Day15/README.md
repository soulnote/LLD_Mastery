# Day 15: Data Modeling Fundamentals 💾

## 🎯 Learning Objectives
- Understand database design fundamentals
- Learn entity-relationship modeling
- Master database normalization (1NF, 2NF, 3NF)
- Design scalable database schemas
- Handle real-world data modeling scenarios

---

## 📚 Theory: Deep Dive

### 1. What is Data Modeling?

Data modeling is the process of creating a visual representation of data systems and their relationships. It serves as the foundation for database design and helps ensure data integrity, efficiency, and scalability.

```
┌─────────────────────────────────────────────────────────────────┐
│                    DATA MODELING LAYERS                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   ┌─────────────┐    ┌─────────────┐    ┌─────────────┐       │
│   │  Conceptual│    │  Logical    │    │  Physical   │       │
│   │    Model   │───▶│    Model    │───▶│    Model    │       │
│   └─────────────┘    └─────────────┘    └─────────────┘       │
│         │                  │                   │               │
│         ▼                  ▼                   ▼               │
│   "What to store"    "How to structure"  "How to implement"  │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### 2. Entity-Relationship (ER) Fundamentals

#### Entities
An entity represents a real-world object that can be identified uniquely.

| Entity | Examples |
|--------|----------|
| User | Customer, Employee, Admin |
| Product | Item, Service, Subscription |
| Transaction | Order, Payment, Refund |

#### Attributes
Attributes describe properties of entities:

```
┌──────────────────────────────┐
│         USER                │
├──────────────────────────────┤
│ PK  user_id       INTEGER   │  ← Unique identifier
│      email        VARCHAR   │  ← Unique, not null
│      name         VARCHAR   │  ← Required
│      phone        VARCHAR   │  ← Optional
│      created_at   TIMESTAMP │  ← Auto-generated
│      is_active    BOOLEAN   │  ← Default true
└──────────────────────────────┘
```

#### Types of Attributes:
1. **Simple**: Single atomic value (e.g., name, email)
2. **Composite**: Multiple simple values (e.g., address = street + city + zip)
3. **Single-valued**: One value per entity (e.g., date of birth)
4. **Multi-valued**: Multiple values per entity (e.g., phone numbers)
5. **Derived**: Calculated from other attributes (e.g., age from DOB)

#### Relationships

| Relationship | Description | Example |
|--------------|-------------|---------|
| One-to-One (1:1) | Each record in Table A relates to one record in Table B | User → UserProfile |
| One-to-Many (1:N) | One record in Table A relates to multiple records in Table B | Category → Products |
| Many-to-Many (M:N) | Multiple records in Table A relate to multiple records in Table B | Student ↔ Course |

```
RELATIONSHIP EXAMPLES:

1:1 (One-to-One)
    ┌─────────┐         ┌─────────────┐
    │  User   │────────▶│ UserProfile │
    └─────────┘         └─────────────┘
    (1)                 (1)

1:N (One-to-Many)
    ┌───────────┐       ┌─────────────┐
    │ Category  │──────▶│  Products   │
    └───────────┘       └─────────────┘
    (1)                 (N)

M:N (Many-to-Many)
    ┌─────────┐     ┌──────────────┐    ┌─────────┐
    │ Student │────▶│ Enrollments  │◀────│ Course  │
    └─────────┘     └──────────────┘    └─────────┘
    (M)             (junction table)     (N)
```

### 3. Database Normalization

Normalization is the process of organizing data to minimize redundancy and improve data integrity.

#### First Normal Form (1NF)

**Requirements:**
- Each column contains atomic (indivisible) values
- Each column contains values of a single type
- Each column has a unique name
- Order of rows and columns doesn't matter

**Violation Example:**
```
❌ NOT IN 1NF (multi-valued attribute):
┌────────────┬─────────────────────┐
│ student_id │      subjects        │
├────────────┼─────────────────────┤
│     1      │  Math, Physics, Chem │
│     2      │  Biology, Chemistry │
└────────────┴─────────────────────┘

✅ IN 1NF:
┌────────────┬────────────┐
│ student_id │  subject    │
├────────────┼────────────┤
│     1      │    Math    │
│     1      │  Physics   │
│     1      │   Chem     │
│     2      │  Biology   │
│     2      │  Chemistry │
└────────────┴────────────┘
```

#### Second Normal Form (2NF)

**Requirements:**
- Must be in 1NF
- No partial dependencies (non-key attributes must depend on the entire primary key)

**Violation Example:**
```
❌ NOT IN 2NF (partial dependency):
┌────────────┬────────────┬────────────┬──────────────┐
│ order_id   │ product_id │ product_name│  quantity    │
├────────────┼────────────┼────────────┼──────────────┤
│     1      │     101    │   Laptop   │      2       │
│     1      │     102    │   Mouse    │      5       │
└────────────┴────────────┴────────────┴──────────────┘
    └────────────────┬─────────────────┘
         Partial dependency: product_name depends only on product_id

✅ IN 2NF:
┌────────────┬────────────┬────────────┐
│ order_id   │ product_id │  quantity  │
├────────────┼────────────┼────────────┤
│     1      │     101    │      2     │
│     1      │     102    │      5     │
└────────────┴────────────┴────────────┘

Products Table:
┌────────────┬────────────┐
│ product_id │ product_name│
├────────────┼────────────┤
│     101    │   Laptop   │
│     102    │   Mouse    │
└────────────┴────────────┘
```

#### Third Normal Form (3NF)

**Requirements:**
- Must be in 2NF
- No transitive dependencies (non-key attributes should depend only on the primary key)

**Violation Example:**
```
❌ NOT IN 3NF (transitive dependency):
┌────────────┬────────────┬────────────┬──────────────┐
│ employee_id│ dept_id    │ dept_name  │   location   │
├────────────┼────────────┼────────────┼──────────────┤
│     1      │     D1     │   Sales    │   Building A │
│     2      │     D2     │  Engineering│  Building B │
└────────────┴────────────┴────────────┴──────────────┘
    └───────────────┬─────────────────┘
        Transitive: location depends on dept_name, not employee_id

✅ IN 3NF:
Employees Table:
┌────────────┬────────────┐
│ employee_id│  dept_id   │
├────────────┼────────────┤
│     1      │     D1    │
│     2      │     D2    │
└────────────┴────────────┘

Departments Table:
┌────────────┬────────────┬──────────────┐
│  dept_id   │ dept_name  │   location   │
├────────────┼────────────┼──────────────┤
│     D1     │   Sales   │   Building A │
│     D2     │ Engineering│  Building B │
└────────────┴────────────┴──────────────┘
```

### 4. Keys in Database Design

```
┌────────────────────────────────────────────────────────────────┐
│                        KEY TYPES                               │
├────────────────────────────────────────────────────────────────┤
│                                                                 │
│  PRIMARY KEY (PK)                                              │
│  ┌──────────────────────────────────────────────────────────┐ │
│  │ • Uniquely identifies each row                          │ │
│  │ • Cannot be NULL                                         │ │
│  │ • Only one per table                                     │ │
│  │ • Example: user_id, order_id, product_id               │ │
│  └──────────────────────────────────────────────────────────┘ │
│                                                                 │
│  FOREIGN KEY (FK)                                              │
│  ┌──────────────────────────────────────────────────────────┐ │
│  │ • Links to primary key in another table                 │ │
│  │ • Can be NULL (optional relationship)                   │ │
│  │ • Can have multiple per table                            │ │
│  │ • Example: category_id references categories(id)        │ │
│  └──────────────────────────────────────────────────────────┘ │
│                                                                 │
│  UNIQUE KEY                                                    │
│  ┌──────────────────────────────────────────────────────────┐ │
│  │ • Ensures unique values (can have multiple per table)   │ │
│  │ • Can be NULL (unless NOT NULL constraint)              │ │
│  │ • Example: email, phone number                           │ │
│  └──────────────────────────────────────────────────────────┘ │
│                                                                 │
│  COMPOSITE KEY                                                │
│  ┌──────────────────────────────────────────────────────────┐ │
│  │ • Combination of 2+ columns to form unique identifier    │ │
│  │ • Example: (order_id, product_id) in order_items table   │ │
│  └──────────────────────────────────────────────────────────┘ │
│                                                                 │
└────────────────────────────────────────────────────────────────┘
```

### 5. Indexing Strategies

Indexes improve query performance but come with write overhead.

```sql
-- Types of Indexes

-- Single Column Index
CREATE INDEX idx_user_email ON users(email);

-- Composite Index (for queries filtering on multiple columns)
CREATE INDEX idx_order_user_date ON orders(user_id, created_at DESC);

-- Unique Index
CREATE UNIQUE INDEX idx_product_sku ON products(sku);

-- Partial Index (PostgreSQL)
CREATE INDEX idx_active_orders ON orders(user_id) WHERE status = 'active';

-- Covering Index (includes all columns needed for query)
CREATE INDEX idx_product_search ON products(category_id, name, price) 
    INCLUDE (description, image_url);
```

**Index Design Best Practices:**
1. Index columns used in WHERE, JOIN, ORDER BY
2. Consider cardinality (high-cardinality columns first)
3. Avoid over-indexing (impacts INSERT/UPDATE performance)
4. Use composite indexes for multi-column queries
5. Consider covering indexes for read-heavy queries

---

## 💡 Interview Questions & Answers

### Q1: What is the difference between OLTP and OLAP?

**Answer:**
| Aspect | OLTP | OLAP |
|--------|------|------|
| **Purpose** | Transaction processing | Analytical processing |
| **Operations** | CRUD (Create, Read, Update, Delete) | Complex queries, aggregations |
| **Data** | Current, detailed | Historical, summarized |
| **Transactions** | Short, frequent | Long, complex |
| **Examples** | Order processing, Banking | Data warehousing, Reporting |
| **Design** | Normalized (3NF) | Denormalized |

### Q2: When would you denormalize a database?

**Answer:**
Denormalization is the process of intentionally adding redundancy for performance. Common scenarios:

1. **Read-heavy applications**: When read performance is critical
2. **Complex reporting**: Aggregated data in data warehouses
3. **Avoiding complex joins**: Pre-computed join results
4. **Cloud NoSQL databases**: DynamoDB, Cassandra recommend denormalization

**Example:**
```sql
-- Normalized (requires JOIN)
SELECT o.order_id, u.name, SUM(oi.quantity * oi.price)
FROM orders o
JOIN users u ON o.user_id = u.id
JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY o.order_id, u.name;

-- Denormalized (pre-computed)
ALTER TABLE orders ADD COLUMN user_name VARCHAR(100);
ALTER TABLE orders ADD COLUMN total_amount DECIMAL(10,2);
-- Updated via triggers or application logic
```

### Q3: How would you design a schema for a ride-sharing app?

**Answer:**
```
Key Entities:
- Users (drivers and riders)
- Trips
- Vehicles
- Payments
- Ratings

Key Design Decisions:
1. User roles: Single users table with role enum or separate tables?
2. Location data: Geospatial indexing for location-based queries
3. Real-time tracking: Separate time-series data
4. Payment: PCI DSS compliance, tokenization
5. Ratings: Bi-directional (driver rates rider, rider rates driver)
```

### Q4: What are database transactions? Explain ACID properties.

**Answer:**
Transactions ensure reliable database operations:

```
ACID PROPERTIES:

┌─────────────────────────────────────────────────────────────────┐
│                                                                 │
│  Atomicity    │ "All or nothing" - transaction succeeds or    │
│               │ fails as a single unit                          │
│───────────────┼─────────────────────────────────────────────────│
│ Consistency   │ Data moves from one valid state to another     │
│               │ - constraints, triggers, cascades              │
│───────────────┼─────────────────────────────────────────────────│
│ Isolation     │ Concurrent transactions don't interfere       │
│               │ - read phenomena: dirty, non-repeatable, phantom│
│───────────────┼─────────────────────────────────────────────────│
│ Durability    │ Once committed, data persists even with       │
│               │ system failure - use of WAL, replication       │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Q5: What is database sharding?

**Answer:**
Sharding is horizontal partitioning that splits data across multiple databases:

```
                    APPLICATION
                         │
            ┌────────────┼────────────┐
            ▼            ▼            ▼
        ┌───────┐    ┌───────┐    ┌───────┐
        │ Shard │    │ Shard │    │ Shard │
        │   1   │    │   2   │    │   3   │
        │ (A-G) │    │ (H-P) │    │ (Q-Z) │
        └───────┘    └───────┘    └───────┘
        
Sharding Keys:
- User ID (most common)
- Geographic region
- Time (for time-series data)
```

---

## 🔧 Real-World Design Patterns

### 1. Soft Deletes
Instead of physically deleting records, mark them as deleted:

```sql
-- Instead of DELETE FROM users WHERE id = 1;
ALTER TABLE users ADD COLUMN deleted_at TIMESTAMP NULL;
-- Soft delete
UPDATE users SET deleted_at = NOW() WHERE id = 1;
-- Query only active records
SELECT * FROM users WHERE deleted_at IS NULL;
```

### 2. Audit Trail
Track all changes to critical data:

```sql
CREATE TABLE user_audit_log (
    audit_id SERIAL PRIMARY KEY,
    user_id INT,
    action VARCHAR(10),  -- INSERT, UPDATE, DELETE
    old_data JSONB,
    new_data JSONB,
    changed_by INT,
    changed_at TIMESTAMP DEFAULT NOW()
);
```

### 3. EAV (Entity-Attribute-Value)
For flexible schemas with varying attributes:

```sql
-- Good for: Products with many optional attributes
-- Bad for: Fixed schema (performance overhead)

CREATE TABLE product_attributes (
    product_id INT REFERENCES products(id),
    attribute_name VARCHAR(50),
    attribute_value VARCHAR(255),
    PRIMARY KEY (product_id, attribute_name)
);
```

---

## 📋 Summary

| Concept | Key Takeaway |
|---------|-------------|
| Entities | Real-world objects with unique identifiers |
| Relationships | 1:1, 1:N, M:N cardinality |
| 1NF | Atomic values, single-type columns |
| 2NF | No partial dependencies |
| 3NF | No transitive dependencies |
| Keys | PK, FK, Unique, Composite |
| Indexes | Speed up reads, slow down writes |

---

## 🎯 Today's Exercise
Design a comprehensive e-commerce database schema with proper normalization, considering users, products, orders, payments, and reviews.

**Expected Duration:** 45 minutes
**Difficulty:** ⭐⭐⭐☆☆

---

## 🔗 Additional Resources
- [Database Normalization - Wikipedia](https://en.wikipedia.org/wiki/Database_normalization)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [Database Sharding Patterns](https://aws.amazon.com/database/)

---
**"Good data design is the foundation of scalable systems"** 🏗️
