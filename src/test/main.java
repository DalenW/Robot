package test;

import com.ward.Console;
import robot.Arduino;
import robot.Motor;
import robot.Servo;

public class main {
    public static void main(String[] args) {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        System.out.println("Starting");
        
        Arduino a = new Arduino("Arduino Uno", "COM3", 0, 1);
        
        Motor m1 = new Motor("Motor 1");
        Motor m2 = new Motor("Motor 2");
        Servo s1 = new Servo("Servo 1");
        
        s1.setValue(0);
        
        Motor[] m = new Motor[2];
        m[0] = m1;
        m[1] = m2;
        
        Servo[] s = new Servo[1];
        s[0] = s1;
        
        a.setServos(s);
        
        
        while(true){
            s[0].setValue(180);
            a.setServos(s);
            a.write();
        }
    }

}
