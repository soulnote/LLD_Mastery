/**
 * EXAMPLE: Facade Pattern
 */
public class FacadePattern {
    
    static class CPU { public void freeze() { System.out.println("CPU freeze"); } }
    static class Memory { public void load() { System.out.println("Memory load"); } }
    static class HardDrive { public void read() { System.out.println("HardDrive read"); } }
    
    static class ComputerFacade {
        private CPU cpu = new CPU();
        private Memory memory = new Memory();
        private HardDrive hd = new HardDrive();
        
        public void start() {
            cpu.freeze();
            memory.load();
            hd.read();
            System.out.println("Computer started!");
        }
    }
    
    public static void main(String[] args) {
        new ComputerFacade().start();
    }
}
