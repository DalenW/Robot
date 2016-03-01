package test;



import com.ward.Console;
import robot.Arduino;
import robot.Motor;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        System.out.println("Starting");
        
        
        Arduino a = new Arduino("Arduino Uno", 115200);
        a.connect();
        System.out.println(a.getCOM());
        
        Motor m1 = new Motor("Elevation");
        m1.setValue(1);
        System.out.println("Done");
        
        while(true){
            System.out.println(a.getOutput("0000" + m1.getValueHex()));
            a.write("0000" + m1.getValueHex());
        }
    }
}
