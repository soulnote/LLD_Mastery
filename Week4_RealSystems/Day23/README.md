# Day 23: Designing a URL Shortener Service 🔗

## 🎯 Learning Objectives
- Understand URL shortening algorithms
- Learn about database design for key-value mappings
- Master hash functions and unique ID generation
- Handle high traffic with caching and load balancing

---

## 📚 Theory: Deep Dive

### 1. What is URL Shortening?

URL shortening is a technique that converts a long URL into a shorter, more manageable URL that redirects to the original URL.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    URL SHORTENING PROCESS                               │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   Original URL:                                                        │
│   https://www.example.com/products/category/item?id=12345&ref=share  │
│                                                                         │
│   ┌─────────────────────────────────────────────────────────────┐     │
│   │                    URL SHORTENER                            │     │
│   │  1. Generate unique short code (e.g., "abc123")          │     │
│   │  2. Store mapping: short_code → original_url              │     │
│   │  3. Return shortened URL: https://short.url/abc123        │     │
│   └─────────────────────────────────────────────────────────────┘     │
│                                                                         │
│   Redirect: https://short.url/abc123 → Original URL                  │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 2. Key Components

```
┌─────────────────────────────────────────────────────────────────────────┐
│                  URL SHORTENER ARCHITECTURE                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   ┌──────────┐     ┌──────────────┐     ┌──────────────────┐       │
│   │  Client  │────▶│ Load Balancer│────▶│ Application      │       │
│   └──────────┘     └──────────────┘     │ Server           │       │
│                                            │                  │       │
│   ┌───────────────────────────────────────│──────────────────│       │
│   │                                       ▼                  │       │
│   │                              ┌──────────────┐        │       │
│   │                              │  Short Code   │        │       │
│   │                              │  Generator    │        │       │
│   │                              └──────────────┘        │       │
│   │                                       │                  │       │
│   │                                       ▼                  │       │
│   │                              ┌──────────────┐        │       │
│   │                              │   Cache       │        │       │
│   │                              │  (Redis)      │        │       │
│   │                              └──────────────┘        │       │
│   │                                       │                  │       │
│   │                                       ▼                  │       │
│   │                              ┌──────────────┐        │       │
│   │                              │  Database    │        │       │
│   │                              │ (SQL/NoSQL)  │        │       │
│   │                              └──────────────┘        │       │
│   │                                                     │       │
│   └─────────────────────────────────────────────────────┘       │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 3. Short Code Generation Algorithms

#### A. Hash-based (MD5/SHA)

```java
// Use first 7 characters of MD5 hash
String hash = md5(originalUrl);
String shortCode = hash.substring(0, 7);
```

**Pros:** Deterministic, no storage needed for ID
**Cons:** Potential collisions, security concern (predictable)

#### B. Base62 Encoding

```java
// Convert auto-increment ID to base62
// 0-9 (10) + a-z (26) + A-Z (26) = 62 characters
String base62 = convertToBase62(123456789);
// Result: "2mrnK7"
```

**Pros:** Short codes, no collisions
**Cons:** Requires sequential ID generation

#### C. Random Character Generation

```java
// Generate random 7-character string
String shortCode = generateRandomString(7);
// Result: "aB3xYz1"
```

**Pros:** Simple to implement
**Cons:** Collision risk, need to check uniqueness

### 4. Database Schema

```sql
-- SQL Schema
CREATE TABLE urls (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    short_code VARCHAR(10) UNIQUE NOT NULL,
    original_url TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NULL,
    click_count INT DEFAULT 0,
    INDEX idx_short_code (short_code)
);
```

```json
// NoSQL (MongoDB)
{
    "_id": "abc123",
    "original_url": "https://...",
    "created_at": "2024-01-01",
    "clicks": 0
}
```

### 5. API Design

```
┌─────────────────────────────────────────────────────────────────────────┐
│                        API ENDPOINTS                                    │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   POST /api/v1/shorten                                                 │
│   ───────────────────────                                              │
│   Request:  { "url": "https://long-url.com" }                         │
│   Response: { "short_url": "https://short.io/abc123" }               │
│                                                                         │
│   GET /api/v1/{shortCode}                                              │
│   ───────────────────────                                              │
│   Response: Redirect to original URL                                    │
│                                                                         │
│   GET /api/v1/{shortCode}/stats                                        │
│   ─────────────────────────────                                        │
│   Response: { "clicks": 100, "created": "2024-01-01" }               │
│                                                                         │
│   DELETE /api/v1/{shortCode}                                           │
│   ───────────────────────                                              │
│   Response: { "message": "URL deleted" }                              │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 6. Caching Strategy

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    CACHING FOR URL SHORTENER                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   1. Cache frequently accessed URLs                                    │
│   2. Use LRU (Least Recently Used) eviction                           │
│   3. Set appropriate TTL (Time To Live)                               │
│                                                                         │
│   Redis Cache:                                                          │
│   ───────────                                                          │
│   Key: "url:abc123" → Value: "https://original-url.com"              │
│   TTL: 1 hour                                                         │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 💡 Interview Questions & Answers

### Q1: How would you handle millions of URL shortenings per day?

**Answer:**
```
1. Database Sharding: Split data across multiple servers
2. Caching: Use Redis for hot URLs
3. Read/Write Separation: Master for writes, replicas for reads
4. Async Processing: Queue analytics updates
5. CDN: Cache redirects at edge locations
```

### Q2: How do you prevent URL collisions?

**Answer:**
```
1. Use unique auto-increment IDs
2. Check existence before creating
3. Use longer short codes (7-8 characters)
4. Implement retry mechanism with alternative codes
5. Base62 encoding ensures no special characters
```

### Q3: What happens if the database goes down?

**Answer:**
```
1. Implement circuit breaker pattern
2. Return error with retry-after header
3. Cache recent URLs in memory
4. Use read-replicas for fallback
5. Consider eventual consistency for stats
```

### Q4: How would you implement custom short codes?

**Answer:**
```
1. Allow users to specify custom code (if available)
2. Validate format (alphanumeric only, length limits)
3. Check uniqueness before storing
4. Reserve system codes (admin, api, etc.)
5. Charge/premium for custom codes
```

---

## 📋 Summary

| Component | Technology Choice |
|-----------|------------------|
| **Short Code** | Base62 encoding of auto-increment ID |
| **Database** | MySQL (PostgreSQL) or NoSQL (MongoDB) |
| **Cache** | Redis for hot URLs |
| **API** | REST with JSON |
| **Scaling** | Horizontal with database sharding |

---

## 🎯 Today's Exercise
Design and implement a URL shortener service with the following features:
1. Shorten a long URL
2. Redirect to original URL
3. Track click count
4. Handle concurrent requests

**Expected Duration:** 45 minutes
**Difficulty:** ⭐⭐⭐☆☆

---

## 🔗 Additional Resources
- [Bitly Architecture](https://bitly.com/)
- [URL Shortener System Design](https://www.systemdesign.com/url-shortener)

---
**"Simplicity is the ultimate sophistication"** 🔗
