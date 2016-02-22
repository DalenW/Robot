package test;



import com.ward.Console;
import robot.Arduino;
import robot.Joystick;
import robot.Motor;
import robot.Servo;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        System.out.println("Starting");
        
        Joystick j = new Joystick("Logitech Extreme 3D");
        //j.connect();
        
        Arduino a = new Arduino("Arduino Uno", "COM4", 115200);
        a.connect();
        
        Servo s1 = new Servo("Servo 1");
        s1.setValue(0);
        System.out.println("Writing");
        System.out.println(a.getOutput(s1.getValueHex()));
        while(true){
            
            a.write(s1.getValueHex());
            //System.out.println("X: " + j.getX() + " Y: " + j.getY() + " R: " + j.getRotation());
            Thread.sleep(10);
        }
    }
}
