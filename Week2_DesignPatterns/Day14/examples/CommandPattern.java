/**
 * EXAMPLE: Command Pattern
 */
public class CommandPattern {
    
    interface Command { void execute(); }
    
    static class Light {
        public void on() { System.out.println("Light ON"); }
        public void off() { System.out.println("Light OFF"); }
    }
    
    static class LightOnCommand implements Command {
        private Light light;
        LightOnCommand(Light l) { this.light = l; }
        public void execute() { light.on(); }
    }
    
    static class LightOffCommand implements Command {
        private Light light;
        LightOffCommand(Light l) { this.light = l; }
        public void execute() { light.off(); }
    }
    
    static class RemoteControl {
        private Command command;
        public void setCommand(Command c) { this.command = c; }
        public void press() { command.execute(); }
    }
    
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();
        Light light = new Light();
        
        remote.setCommand(new LightOnCommand(light));
        remote.press();
        
        remote.setCommand(new LightOffCommand(light));
        remote.press();
    }
}
