/**
 * EXAMPLE: Observer Pattern
 */
import java.util.*;
public class ObserverPattern {
    
    interface Observer { void update(String msg); }
    
    static class Subject {
        private List<Observer> observers = new ArrayList<>();
        
        public void attach(Observer o) { observers.add(o); }
        public void detach(Observer o) { observers.remove(o); }
        public void notifyAll(String msg) {
            for (Observer o : observers) o.update(msg);
        }
    }
    
    static class NewsChannel implements Observer {
        private String name;
        NewsChannel(String n) { this.name = n; }
        public void update(String msg) {
            System.out.println(name + " received: " + msg);
        }
    }
    
    public static void main(String[] args) {
        Subject news = new Subject();
        news.attach(new NewsChannel("CNN"));
        news.attach(new NewsChannel("BBC"));
        news.notifyAll("Breaking news!");
    }
}
