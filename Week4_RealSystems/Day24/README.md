# Day 24: Designing a Distributed Cache System 💾

## 🎯 Learning Objectives
- Understand cache fundamentals and eviction policies
- Learn about cache strategies (write-through, write-back, cache-aside)
- Master distributed caching with Redis/Memcached
- Handle cache invalidation and consistency

---

## 📚 Theory: Deep Dive

### 1. What is Caching?

Caching is a technique to store frequently accessed data in fast storage to reduce latency and database load.

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    CACHING ARCHITECTURE                                 │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   ┌─────────┐        ┌─────────┐        ┌─────────┐                  │
│   │ Request │───────▶│ Cache   │───────▶│ Database│                  │
│   └─────────┘        └─────────┘        └─────────┘                  │
│                          │                      │                       │
│                          │ cache hit            │                       │
│                          ▼                      ▼ cache miss           │
│                    ┌─────────┐        ┌─────────┐                    │
│                    │  Data   │        │  Store  │                    │
│                    │  Found  │        │   new   │                    │
│                    └─────────┘        └─────────┘                    │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 2. Cache Eviction Policies

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    EVICTION POLICIES                                    │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   LRU (Least Recently Used)                                            │
│   ─────────────────────────                                            │
│   • Evicts least recently accessed items                               │
│   • Good for temporal locality                                         │
│   • Implemented with LinkedHashMap                                     │
│                                                                         │
│   LFU (Least Frequently Used)                                          │
│   ───────────────────────────                                          │
│   • Evicts least frequently accessed items                            │
│   • Good for frequently accessed data                                 │
│   • Requires tracking access counts                                    │
│                                                                         │
│   FIFO (First In First Out)                                            │
│   ──────────────────────────                                           │
│   • Evicts oldest items regardless of access                           │
│   • Simple to implement                                                │
│   • Good for cache with time-based expiration                          │
│                                                                         │
│   TTL (Time To Live)                                                   │
│   ───────────────────                                                  │
│   • Evicts items after specified time                                  │
│   • Used with other policies                                           │
│   • Prevents stale data                                                │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 3. Cache Strategies

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    CACHE STRATEGIES                                    │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   CACHE-ASIDE                    WRITE-THROUGH                         │
│   ============                     =============                        │
│                                                                         │
│   App         Cache     DB         App      Cache       DB             │
│   ───         ─────     ───        ───      ─────       ──             │
│   1.Request   miss      fetch      Request  hit         -              │
│   2.          fill      -          Write    update      sync           │
│   3.          -         -          -        -           -              │
│                                                                         │
│   • Read-heavy workloads          • Write-heavy workloads              │
│   • Simple to implement           • Strong consistency                 │
│   • Cache failure doesn't affect • Slower writes                       │
│                                                                         │
│   WRITE-BEHIND                  REFRESH-AHEAD                          │
│   =============                 ==============                          │
│                                                                         │
│   App      Cache      DB         App      Cache       DB               │
│   ───      ─────      ───        ───      ─────       ──               │
│   Write    update     async      Request  hit         -                 │
│   -        -          queue      -        refresh    -                 │
│   -        -          batch      -        -          -                 │
│                                                                         │
│   • Fast writes                 • Predictable reads                    │
│   • Risk of data loss           • Pre-warming cache                   │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 4. Distributed Cache Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│              DISTRIBUTED CACHE ARCHITECTURE                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│                         ┌─────────────┐                                │
│                    ┌───▶│Load Balancer│───┐                         │
│                    │    └─────────────┘   │                         │
│                    │                      │                           │
│      ┌─────────────┼──────────────────────┼─────────────┐           │
│      │             │                      │             │           │
│      ▼             ▼                      ▼             ▼           │
│   ┌──────┐    ┌──────┐              ┌──────┐    ┌──────┐         │
│   │Server│    │Server│              │Server│    │Server│         │
│   │  1   │    │  2   │              │  3   │    │  4   │         │
│   └──┬───┘    └──┬───┘              └──┬───┘    └──┬───┘         │
│      │           │                      │            │              │
│      └───────────┴──────────────────────┴────────────┘              │
│                          │                                          │
│                          ▼                                          │
│                    ┌─────────────┐                                  │
│                    │    Redis    │                                  │
│                    │   Cluster   │                                  │
│                    │             │                                  │
│                    │  Node1      │                                  │
│                    │  Node2      │                                  │
│                    │  Node3      │                                  │
│                    └─────────────┘                                  │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 5. Cache Invalidation Strategies

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    INVALIDATION STRATEGIES                             │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   TTL-BASED                    EVENT-BASED                             │
│   =========                    ============                            │
│                                                                         │
│   • Set expiration time        • Listen to database events            │
│   • Simple but inflexible      • Immediate invalidation               │
│   • May serve stale data       • More complex                         │
│                                                                         │
│   WRITE-THROUGH                ACTIVE INVALIDATION                     │
│   ===============             ==================                      │
│                                                                         │
│   • Update cache on write      • Broadcast invalidation                │
│   • Always consistent         • Requires coordination                 │
│   • Slower writes             • More complex                           │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### 6. Cache Metrics

| Metric | Description |
|--------|-------------|
| **Hit Rate** | Percentage of requests served from cache |
| **Miss Rate** | Percentage requiring database fetch |
| **Latency** | Time to serve requests |
| **Throughput** | Requests per second |
| **Eviction Count** | Items evicted |
| **Memory Usage** | Cache size utilization |

---

## 💡 Interview Questions & Answers

### Q1: How would you implement an LRU cache?

**Answer:**
```
Use LinkedHashMap with accessOrder=true:
- maintain insertion order for regular LRU
- maintain access order for LRU
- Override removeEldestEntry() to control size

For distributed: Use Redis with sorted sets
- Score = timestamp
- ZREMRANGEBYSCORE for eviction
```

### Q2: What happens when the cache goes down?

**Answer:**
```
1. Fallback to database
2. Implement circuit breaker
3. Use multiple cache layers
4. Cache warm-up on recovery
5. Monitor and alert
```

### Q3: How do you handle cache consistency?

**Answer:**
```
1. Write-through: Update cache and DB together
2. Write-behind: Async DB updates
3. Cache expiration with short TTL
4. Event-based invalidation
5. Version/timestamp checking
```

### Q4: How would you scale the cache?

**Answer:**
```
1. Redis Cluster with sharding
2. Consistent hashing for distribution
3. Replication for high availability
4. Read replicas for scaling reads
5. Connection pooling
```

---

## 📋 Summary

| Aspect | Choice |
|--------|--------|
| **Eviction Policy** | LRU or LFU |
| **Strategy** | Cache-aside for read-heavy |
| **Invalidation** | TTL + Event-based |
| **Distributed** | Redis Cluster |
| **Consistency** | Write-through |

---

## 🎯 Today's Exercise
Design and implement an LRU cache with the following features:
1. Thread-safe operations
2. Configurable capacity
3. Eviction of least recently used items
4. Get and put operations

**Expected Duration:** 45 minutes
**Difficulty:** ⭐⭐⭐☆☆

---

## 🔗 Additional Resources
- [Redis Documentation](https://redis.io/documentation)
- [Cache Design Patterns](https://cache2k.org/documentation/)

---
**"Cache is the king of performance"** 💾
