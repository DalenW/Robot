package test;



import com.ward.Console;
import robot.Arduino;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        System.out.println("Starting");
        
        
        Arduino a = new Arduino("Arduino Uno", "COM5", 115200);
        a.connectW();
        
        System.out.println("Done");
    }
}
