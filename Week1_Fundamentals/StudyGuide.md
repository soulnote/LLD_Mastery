# Week 1 Study Guide: OOP Fundamentals & SOLID Principles 📚

## 🎯 Learning Path Overview

### Day 1-2: OOP Fundamentals
**Master the four pillars of Object-Oriented Programming**

#### Encapsulation 🔒
- **What**: Bundling data and methods, controlling access
- **Why**: Data protection, maintainability, flexibility
- **How**: Private fields, public methods, getters/setters
- **Example**: BankAccount with private balance

#### Inheritance 🧬
- **What**: IS-A relationship, code reusability
- **Why**: Avoid code duplication, establish hierarchies
- **How**: extends keyword, super() calls
- **Example**: Vehicle → Car, Motorcycle

#### Polymorphism 🎭
- **What**: One interface, multiple implementations
- **Why**: Flexibility, extensibility, maintainability
- **How**: Method overriding, interfaces, abstract classes
- **Example**: PaymentProcessor → CreditCard, PayPal, Crypto

#### Abstraction 🎨
- **What**: Hide implementation details, show essential features
- **Why**: Simplicity, focus on what not how
- **How**: Abstract classes, interfaces
- **Example**: MediaPlayer interface with different implementations

### Day 3-5: SOLID Principles
**Build maintainable and extensible software**

#### Single Responsibility Principle (SRP) 🎯
- **Definition**: A class should have only one reason to change
- **Benefits**: Easier testing, maintenance, debugging
- **Violations**: God classes, mixed concerns
- **Solution**: Extract separate classes for different responsibilities

#### Open/Closed Principle (OCP) 🚪
- **Definition**: Open for extension, closed for modification
- **Benefits**: Add new features without changing existing code
- **Implementation**: Interfaces, abstract classes, strategy pattern
- **Example**: Adding new payment methods without changing existing code

#### Liskov Substitution Principle (LSP) 🔄
- **Definition**: Subtypes must be substitutable for their base types
- **Benefits**: Reliable inheritance hierarchies
- **Violations**: Strengthening preconditions, weakening postconditions
- **Example**: Rectangle/Square problem

#### Interface Segregation Principle (ISP) 🧩
- **Definition**: Clients shouldn't depend on interfaces they don't use
- **Benefits**: Smaller, focused interfaces
- **Violations**: Fat interfaces, forced implementations
- **Solution**: Split large interfaces into smaller, specific ones

#### Dependency Inversion Principle (DIP) 🔄
- **Definition**: Depend on abstractions, not concretions
- **Benefits**: Loose coupling, easier testing, flexibility
- **Implementation**: Dependency injection, interfaces
- **Example**: Service classes depending on interfaces, not implementations

## 📖 Key Concepts Cheat Sheet

### Access Modifiers
```java
public    // Accessible everywhere
protected // Accessible in package and subclasses
default   // Accessible in package only
private   // Accessible in class only
```

### Method Overriding vs Overloading
```java
// Overriding (Runtime Polymorphism)
@Override
public void method() { /* different implementation */ }

// Overloading (Compile-time Polymorphism)
public void method(int x) { }
public void method(String s) { }
public void method(int x, int y) { }
```

### Abstract Class vs Interface
```java
// Abstract Class (IS-A relationship)
abstract class Animal {
    protected String name;           // Can have fields
    public abstract void sound();   // Abstract method
    public void sleep() { }         // Concrete method
}

// Interface (CAN-DO relationship)
interface Flyable {
    void fly();                     // Implicitly public abstract
    default void glide() { }        // Default implementation (Java 8+)
}
```

## 🏋️ Practice Exercises Summary

### Day 1 Exercises
1. **Library Management** - Encapsulation with Book class
2. **Shape Hierarchy** - Inheritance with geometric shapes
3. **Employee System** - Combined encapsulation and inheritance

### Day 2 Exercises
1. **Calculator System** - Method overloading and polymorphism
2. **Notification System** - Abstraction with multiple notification types

### Day 3-5 Exercises
1. **User Service Refactoring** - SRP violation and solution
2. **Report Generation** - Separating data, formatting, delivery
3. **E-commerce Order** - Multiple SOLID principles

## 🎯 Common Mistakes to Avoid

### Encapsulation Mistakes
- ❌ Making all fields public
- ❌ Not validating in setters
- ❌ Exposing internal collections directly

### Inheritance Mistakes
- ❌ Using inheritance for code reuse only (prefer composition)
- ❌ Deep inheritance hierarchies
- ❌ Violating LSP in subclasses

### Polymorphism Mistakes
- ❌ Not using interfaces for flexibility
- ❌ Tight coupling to concrete classes
- ❌ Not leveraging runtime polymorphism

### SOLID Violations
- ❌ God classes doing everything (SRP)
- ❌ Modifying existing code for new features (OCP)
- ❌ Subclasses changing expected behavior (LSP)
- ❌ Fat interfaces with unused methods (ISP)
- ❌ Depending on concrete implementations (DIP)

## 📚 Recommended Reading

### Books
1. **"Clean Code"** by Robert C. Martin - SOLID principles
2. **"Head First Design Patterns"** - OOP and patterns
3. **"Effective Java"** by Joshua Bloch - Java best practices

### Online Resources
1. **Oracle Java Tutorials** - OOP concepts
2. **Refactoring Guru** - Design patterns and principles
3. **Clean Code Blog** - Robert C. Martin's articles

## 🧪 Self-Assessment Checklist

### After Day 1-2 (OOP Fundamentals)
- [ ] Can explain all four OOP pillars with examples
- [ ] Can identify when to use inheritance vs composition
- [ ] Can implement method overriding and overloading
- [ ] Can design classes with proper encapsulation
- [ ] Can use abstract classes and interfaces appropriately

### After Day 3-5 (SOLID Principles)
- [ ] Can identify SRP violations in code
- [ ] Can refactor classes to follow SRP
- [ ] Understand how OCP enables extensibility
- [ ] Can design substitutable inheritance hierarchies (LSP)
- [ ] Can create focused, cohesive interfaces (ISP)
- [ ] Can implement dependency inversion (DIP)

### Week 1 Mastery Indicators
- [ ] Score 80%+ on Week 1 Assessment Quiz
- [ ] Can explain OOP concepts to others
- [ ] Can identify and fix SOLID violations
- [ ] Can design simple class hierarchies
- [ ] Ready to learn design patterns (Week 2)

## 🚀 Next Steps

### Preparing for Week 2
1. **Review** any concepts you struggled with
2. **Practice** more exercises if needed
3. **Read** about design patterns introduction
4. **Set up** your development environment for Week 2

### Week 2 Preview: Design Patterns
- Creational Patterns (Singleton, Factory, Builder)
- Structural Patterns (Adapter, Decorator, Facade)
- Behavioral Patterns (Observer, Strategy, Command)

---

## 💡 Study Tips

1. **Code Daily**: Practice coding examples every day
2. **Teach Others**: Explain concepts to solidify understanding
3. **Real Examples**: Think of real-world applications
4. **Refactor Code**: Take existing code and improve it
5. **Ask Questions**: Don't hesitate to seek clarification

**Remember**: Mastery comes through consistent practice and application! 🎯