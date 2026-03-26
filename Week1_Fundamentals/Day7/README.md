# Day 7: UML & Class Diagrams 📊

## 🎯 Today's Goals
- Master UML class diagram notation
- Understand relationships between classes
- Create visual representations of code
- Communicate designs effectively

## 📚 Theory (Hour 1)

### Why UML? 📝
- Visual representation of code
- Standard notation for design communication
- Helps in planning and documentation
- Essential for system architects

### UML Class Diagram Basics 📊

**Class Notation**:
```
+-----------------+
|   ClassName     |
+-----------------+
| - field: Type   |
+-----------------+
| + method()      |
+-----------------+
```

**Visibility**:
- `+` Public
- `-` Private
- `#` Protected
- `~` Package (default)

### Relationship Types 🔗

1. **Association** (→)
   - Basic relationship
   - One class uses another

2. **Aggregation** (◇)
   - "Has-a" relationship
   - Parts can exist without whole

3. **Composition** (◆)
   - Strong "has-a"
   - Parts cannot exist without whole

4. **Inheritance** (△)
   - "Is-a" relationship
   - Extends another class

5. **Dependency** (-->) 
   - Uses temporarily
   - Weaker relationship

### UML Best Practices 📋
- Keep diagrams simple
- Focus on key relationships
- Use consistent naming
- Document complex relationships

## 💻 Practical Focus
- Drawing class diagrams
- Identifying relationships
- Converting diagrams to code

## 🏋️ Today's Exercises
1. **Draw Library System**: Create UML diagram for library system
2. **Convert to Code**: Implement from UML diagram

## 📝 Success Criteria
- [ ] Understand UML class diagram notation
- [ ] Identify different relationship types
- [ ] Create class diagrams for simple systems
- [ ] Convert diagrams to code

---
**"A picture is worth a thousand words" - UML diagrams communicate complex ideas instantly**
