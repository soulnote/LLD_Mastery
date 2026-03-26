/**
 * SOLUTION: Computer Builder
 */
public class Exercise1_Solution {
    
    static class Computer {
        private String cpu, ram, storage, gpu, display;
        
        private Computer(Builder b) {
            this.cpu = b.cpu; this.ram = b.ram;
            this.storage = b.storage; this.gpu = b.gpu; this.display = b.display;
        }
        
        @Override
        public String toString() {
            return "Computer{cpu=" + cpu + ", ram=" + ram + ", storage=" + storage + 
                   ", gpu=" + gpu + ", display=" + display + "}";
        }
    }
    
    static class Builder {
        private String cpu, ram, storage, gpu, display;
        
        public Builder cpu(String cpu) { this.cpu = cpu; return this; }
        public Builder ram(String ram) { this.ram = ram; return this; }
        public Builder storage(String s) { this.storage = s; return this; }
        public Builder gpu(String gpu) { this.gpu = gpu; return this; }
        public Builder display(String d) { this.display = d; return this; }
        
        public Computer build() { return new Computer(this); }
    }
    
    public static void main(String[] args) {
        Computer gaming = new Builder()
            .cpu("i9").ram("32GB").storage("2TB").gpu("RTX 4090").display("4K").build();
        
        Computer office = new Builder().cpu("i5").ram("16GB").storage("512GB").build();
        
        System.out.println("Gaming: " + gaming);
        System.out.println("Office: " + office);
    }
}
