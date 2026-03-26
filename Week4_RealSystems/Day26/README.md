# Day 26: Designing a Search System 🔍

## 🎯 Learning Objectives
- Understand search engine architecture
- Learn about inverted indexes
- Master query processing and ranking
- Handle real-time search updates

---

## 📚 Theory

### Inverted Index
```
┌─────────────────────────────────────────────────────────────────────────┐
│                    INVERTED INDEX                                        │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│   Document 1: "The quick brown fox"                                   │
│   Document 2: "The slow red fox"                                       │
│   Document 3: "A quick dog"                                            │
│                                                                         │
│   Index:                                                                │
│   ──────                                                                │
│   the     → [doc1, doc2]                                               │
│   quick   → [doc1, doc3]                                               │
│   brown   → [doc1]                                                     │
│   fox     → [doc1, doc2]                                               │
│   slow    → [doc2]                                                     │
│   red     → [doc2]                                                     │
│   dog     → [doc3]                                                     │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

### Search Ranking
- TF-IDF (Term Frequency - Inverse Document Frequency)
- BM25 (Best Matching 25)
- PageRank for web search

---

## 🎯 Exercise
Design an inverted index search system with query matching.

**Difficulty:** ⭐⭐⭐☆☆
