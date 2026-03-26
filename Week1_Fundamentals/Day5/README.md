# Day 5: Interface Segregation & Dependency Inversion 🧩🔄

## 🎯 Today's Goals
- Master the Interface Segregation Principle (ISP)
- Master the Dependency Inversion Principle (DIP)
- Design focused, cohesive interfaces
- Implement dependency injection patterns

## 📚 Theory (Hour 1)

### Interface Segregation Principle (ISP) 🧩
**Definition**: "Clients shouldn't depend on interfaces they don't use" - Robert C. Martin

**Key Concepts**:
- Prefer many small, specific interfaces over one large, general interface
- Clients should only know about methods they use
- Avoid "fat" interfaces with many responsibilities
- Creates more maintainable and flexible code

**Benefits**:
- Smaller, focused interfaces
- Reduced coupling
- Easier to implement and test
- Better code organization

**Violation Examples**:
- One interface with many methods
- Classes forced to implement unused methods
- Difficulty in implementing interface

### Dependency Inversion Principle (DIP) 🔄
**Definition**: "Depend on abstractions, not concretions" - Robert C. Martin

**Key Concepts**:
- High-level modules should not depend on low-level modules
- Both should depend on abstractions
- Abstractions should not depend on details
- Details should depend on abstractions

**Benefits**:
- Loose coupling between modules
- Easier to test (mock dependencies)
- More flexible and maintainable code
- Enables dependency injection

**Implementation**:
- Use interfaces for abstractions
- Inject dependencies via constructors
- Use dependency injection frameworks

## 💻 Practical Focus
- Service layer architecture (DIP)
- Repository pattern (DIP)
- Focused interfaces (ISP)
- Dependency injection

## 🏋️ Today's Exercises
1. **Device Control System**: Implement focused interfaces for different devices
2. **Order Service**: Apply DIP with dependency injection

## 📝 Success Criteria
- [ ] Create focused, cohesive interfaces (ISP)
- [ ] Implement dependency injection (DIP)
- [ ] Depend on abstractions, not concretions
- [ ] Demonstrate loose coupling

---
**"Depend on abstractions, not concretions" - Robert C. Martin**
