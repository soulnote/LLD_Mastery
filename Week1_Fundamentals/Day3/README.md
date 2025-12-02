# Day 3: Single Responsibility Principle (SRP) 🎯

## 🎯 Today's Goals
- Master the Single Responsibility Principle
- Identify SRP violations in existing code
- Refactor code to follow SRP
- Build maintainable and testable classes

## 📚 Theory (Hour 1)

### Single Responsibility Principle 🎯
**Definition**: "A class should have only one reason to change" - Robert C. Martin

**Key Concepts**:
- Each class should have only one job/responsibility
- High cohesion within classes
- Separation of concerns
- Easier testing and maintenance

### Why SRP Matters 🤔
- **Maintainability**: Changes in one area don't affect others
- **Testability**: Easier to write focused unit tests
- **Reusability**: Single-purpose classes are more reusable
- **Debugging**: Easier to locate and fix issues

### Common SRP Violations ❌
- God classes (doing everything)
- Mixed business logic with data access
- UI logic mixed with business logic
- Validation mixed with data processing

### SRP Best Practices ✅
- One class, one responsibility
- Extract helper classes for different concerns
- Use composition to combine responsibilities
- Follow the "Single Reason to Change" rule

## 💻 Practical Focus
- User management system refactoring
- Report generation system
- Email service separation
- Data validation extraction

## 🏋️ Today's Exercises
1. **User Service Refactoring**: Break down a monolithic user service
2. **Report System**: Separate data, formatting, and delivery concerns
3. **E-commerce Order**: Extract validation, calculation, and persistence

## 📝 Success Criteria
- [ ] Identify SRP violations in given code
- [ ] Refactor classes to follow SRP
- [ ] Create focused, single-purpose classes
- [ ] Demonstrate improved testability

---
**"The single responsibility principle is about people." - Robert C. Martin**